package tech.provve.skill.service.application;

import io.avaje.config.Config;
import io.avaje.inject.External;
import io.vertx.core.Vertx;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.exception.IntegrityConstraintViolationException;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.api.server.generated.dto.CastVoteRequest;
import tech.provve.api.server.generated.dto.ExamAddVote;
import tech.provve.api.server.generated.dto.SkillAddVote;
import tech.provve.api.server.generated.dto.SkillDelVote;
import tech.provve.libs.scheduling.Scheduling;
import tech.provve.skill.domain.entity.Vote;
import tech.provve.skill.exception.AuthorCannotVote;
import tech.provve.skill.exception.CastAlreadyExists;
import tech.provve.skill.exception.VoteAlreadyExists;
import tech.provve.skill.exception.VoteNotFound;
import tech.provve.skill.repository.SkillRepository;
import tech.provve.skill.repository.VoteRepository;
import tech.provve.skill.service.SanitizingService;
import terch.provve.libs.s3.S3Service;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Collections.emptyList;
import static tech.provve.accounts.service.JwsParsingService.JWT_SUBJECT;
import static tech.provve.skill.domain.entity.Vote.Type.ADD_SKILL;
import static tech.provve.skill.domain.entity.Vote.Type.DELETE_SKILL;
import static tech.provve.skill.service.SanitizingService.sanitize;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final SkillRepository skillRepository;

    @External
    private final Supplier<LocalDateTime> deadlineSupplier;

    @External
    private final JwsParsingService jwsParsingService;

    @External
    private final Scheduling scheduling;

    @External
    private final S3Service s3Service;

    @External
    private final Vertx vertx;

    @Override
    public void create(SkillAddVote skillAddVote) throws VoteAlreadyExists {
        voteRepository.findByName(skillAddVote.getName())
                      .ifPresent(_ -> {
                          throw new VoteAlreadyExists(skillAddVote.getName());
                      });

        var author = jwsParsingService.parseAuth(skillAddVote.getAuthToken(), JWT_SUBJECT);
        var deadline = deadlineSupplier.get();
        var vote = new Vote(
                sanitize(skillAddVote.getName()),
                true,
                false,
                author,
                deadline,
                sanitize(skillAddVote.getArguments()),
                ADD_SKILL,
                skillAddVote.getTags()
                            .stream()
                            .map(SanitizingService::sanitize)
                            .toList(),
                null,
                null
        );
        voteRepository.save(vote);
        scheduling.addSkill(vote.name(), deadline.toInstant(ZoneOffset.UTC));
    }

    @Override
    public void create(SkillDelVote skillDelVote) throws VoteAlreadyExists {
        voteRepository.findByName(skillDelVote.getName())
                      .ifPresent(_ -> {
                          throw new VoteAlreadyExists(skillDelVote.getName());
                      });

        var author = jwsParsingService.parseAuth(skillDelVote.getAuthToken(), JWT_SUBJECT);
        var deadline = deadlineSupplier.get();
        var vote = new Vote(
                sanitize(skillDelVote.getName()),
                true,
                false,
                author,
                deadline,
                sanitize(skillDelVote.getArguments()),
                DELETE_SKILL,
                emptyList(),
                null,
                null
        );
        voteRepository.save(vote);
        scheduling.delSkill(vote.name(), deadline.toInstant(ZoneOffset.UTC));
    }

    @Override
    public void create(ExamAddVote examAddVote) throws VoteAlreadyExists {
        voteRepository.findByName(examAddVote.getName())
                      .ifPresent(_ -> {
                          throw new VoteAlreadyExists(examAddVote.getName());
                      });

        var author = jwsParsingService.parseAuth(examAddVote.getAuthToken(), JWT_SUBJECT);
        var deadline = deadlineSupplier.get();

        String publicArchiveName = examAddVote.getPublicArchive()
                                              .uploadedFileName();
        String privateArchiveName = examAddVote.getPrivateArchive()
                                               .uploadedFileName();

        uploadToS3(privateArchiveName, S3Service.privateArchiveKeygen(examAddVote.getName()));
        uploadToS3(publicArchiveName, S3Service.publicArchiveKeygen(examAddVote.getName()));

        // запустить МС сохранения (PREPARED.entry =
        //        var exam = new Exam(examAddVote.getName(), examAddVote.getSkillName(), examAddVote.getDescription(), materialUrl);
        //        var vote = new Vote(
        //                sanitize(examAddVote.getName()),
        //                true,
        //                false,
        //                author,
        //                deadline,
        //                sanitize(examAddVote.getArguments()),
        //                DELETE_SKILL,
        //                emptyList(),
        //                exam,
        //                null
        //        );
        //        voteRepository.save(vote);
        //        scheduling.delSkill(vote.name(), deadline.toInstant(ZoneOffset.UTC));
        // )
    }

    private void uploadToS3(String path, String key) {
        s3Service.crtUpload(Config.get("s3.buckets.exams"), key, Path.of(path));
    }

    @Override
    public void cast(String voteName, CastVoteRequest castVote) {
        var voter = jwsParsingService.parseAuth(castVote.getAuthToken(), JWT_SUBJECT);
        voteRepository.findByName(voteName)
                      .ifPresentOrElse(
                              vote -> {
                                  try {
                                      if (voter.equals(vote.author())) {
                                          throw new AuthorCannotVote();
                                      }

                                      voteRepository.setReaction(voteName, voter, castVote.getPositiveReaction());
                                  } catch (IntegrityConstraintViolationException e) {
                                      if (e.getMessage()
                                           .contains("duplicate")) {
                                          throw new CastAlreadyExists();
                                      }
                                  }
                              }, () -> {
                                  throw new VoteNotFound(voteName);
                              }
                      );
    }

    @Override
    @SuppressWarnings("all")
    public boolean end(String voteName) {
        Optional<Vote> optionalVote = voteRepository.findByName(voteName);

        if (optionalVote.isEmpty()) return false;

        var vote = optionalVote.get();
        int positiveRelation = vote.reactions()
                                   .positive() / (0 == vote.reactions()
                                                           .negative() ? 1 : vote.reactions()
                                                                                 .negative());
        if (positiveRelation >= 1) {
            voteRepository.updateSuccess(voteName, true);
            return true;
        } else {
            voteRepository.updateSuccess(voteName, false);
            return false;
        }
    }
}
