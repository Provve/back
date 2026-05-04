package tech.provve.skill.service.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.avaje.config.Config;
import io.avaje.inject.External;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jooq.exception.IntegrityConstraintViolationException;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.api.server.generated.dto.*;
import tech.provve.libs.s3.S3Service;
import tech.provve.libs.scheduling.Scheduling;
import tech.provve.skill.domain.entity.Comment;
import tech.provve.skill.domain.entity.Exam;
import tech.provve.skill.domain.entity.Vote;
import tech.provve.skill.exception.*;
import tech.provve.skill.mapper.vote.VoteResponseMapper;
import tech.provve.skill.repository.CommentRepository;
import tech.provve.skill.repository.SkillRepository;
import tech.provve.skill.repository.VoteRepository;
import tech.provve.skill.service.XssSanitizer;
import tech.provve.statemachine.service.domain.StatemachineService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static tech.provve.accounts.service.JwsParsingService.JWT_SUBJECT;
import static tech.provve.skill.domain.entity.Vote.Type.*;
import static tech.provve.skill.service.XssSanitizer.sanitize;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final SkillRepository skillRepository;
    private final CommentRepository commentRepository;

    @External
    private final ObjectMapper objectMapper;

    @External
    private final Supplier<LocalDateTime> deadlineSupplier;

    @External
    private final JwsParsingService jwsParsingService;

    @External
    private final Scheduling scheduling;

    @External
    private final S3Service s3;

    @External
    private final StatemachineService statemachineService;

    @Override
    public void create(SkillAddVote skillAddVote) throws VoteAlreadyExists {
        voteRepository.findByName(skillAddVote.getName())
                      .ifPresent(_ -> {
                          throw new VoteAlreadyExists(skillAddVote.getName());
                      });

        var author = jwsParsingService.parseAuth(skillAddVote.getAuthToken(), JWT_SUBJECT);
        var deadline = deadlineSupplier.get();
        var vote = Vote.builder()
                       .name(sanitize(skillAddVote.getName()))
                       .active(true)
                       .success(false)
                       .author(author)
                       .deadline(deadline)
                       .arguments(sanitize(skillAddVote.getArguments()))
                       .type(ADD_SKILL)
                       .tags(skillAddVote.getTags()
                                         .stream()
                                         .map(XssSanitizer::sanitize)
                                         .toList())
                       .build();
        voteRepository.save(vote);
        scheduling.addSkill(vote.getName(), deadline.toInstant(ZoneOffset.UTC));
    }

    @Override
    public void create(SkillDelVote skillDelVote) throws VoteAlreadyExists {
        voteRepository.findByName(skillDelVote.getName())
                      .ifPresent(_ -> {
                          throw new VoteAlreadyExists(skillDelVote.getName());
                      });

        var author = jwsParsingService.parseAuth(skillDelVote.getAuthToken(), JWT_SUBJECT);
        var deadline = deadlineSupplier.get();
        var vote = Vote.builder()
                       .name(sanitize(skillDelVote.getName()))
                       .active(true)
                       .success(false)
                       .author(author)
                       .deadline(deadline)
                       .arguments(sanitize(skillDelVote.getArguments()))
                       .type(DEL_SKILL)
                       .tags(skillDelVote.getTags()
                                         .stream()
                                         .map(XssSanitizer::sanitize)
                                         .toList())
                       .build();
        voteRepository.save(vote);
        scheduling.delSkill(vote.getName(), deadline.toInstant(ZoneOffset.UTC));
    }

    @Override
    @SneakyThrows
    public void create(ExamAddVote examAddVote) throws VoteAlreadyExists {
        voteRepository.findByName(examAddVote.getName())
                      .ifPresent(_ -> {
                          throw new VoteAlreadyExists(examAddVote.getName());
                      });
        if (!skillRepository.exists(examAddVote.getSkillName())) {
            throw new SkillNotFound(examAddVote.getSkillName());
        }

        String publicArchive = examAddVote.getPublicArchive()
                                          .uploadedFileName();
        String privateArchive = examAddVote.getPrivateArchive()
                                           .uploadedFileName();

        String bucket = Config.get("s3.buckets.exams");
        String privateArchiveUrl = s3.crtUpload(
                bucket, S3Service.privateArchiveKeygen(examAddVote.getName()),
                Files.readAllBytes(Path.of(privateArchive))
        );
        String publicArchiveUrl = s3.crtUpload(
                bucket, S3Service.publicArchiveKeygen(examAddVote.getName()),
                Files.readAllBytes(Path.of(publicArchive))
        );

        var exam = new Exam(examAddVote.getName(), examAddVote.getSkillName(), examAddVote.getDescription(), privateArchiveUrl, publicArchiveUrl);
        var author = jwsParsingService.parseAuth(examAddVote.getAuthToken(), JWT_SUBJECT);
        var vote = Vote.builder()
                       .name(sanitize(examAddVote.getName()))
                       .active(true)
                       .success(false)
                       .author(author)
                       .arguments(sanitize(examAddVote.getArguments()))
                       .type(ADD_EXAM)
                       .tags(examAddVote.getTags()
                                        .stream()
                                        .map(XssSanitizer::sanitize)
                                        .toList())
                       .exam(exam)
                       .build();

        statemachineService.createSaveExam(examAddVote.getName(), author, objectMapper.writeValueAsString(vote));
    }

    @Override
    public Votes list(CollectionRequest collectionRequest) {
        List<VoteResponse> all = voteRepository.getAll(collectionRequest.getFilter(),
                                                       collectionRequest.getPagination()
                                                                        .getPrevious(),
                                                       collectionRequest.getPagination()
                                                                        .getSize())
                                               .stream()
                                               .map(VoteResponseMapper.INST::map)
                                               .toList();
        if (all.isEmpty()) {
            return new Votes(all, new Cursor(""));
        }

        var cursor = new Cursor(all.getLast()
                                   .getName());
        return new Votes(all, cursor);
    }

    @Override
    public void addComment(AddCommentRequest request, String voteName) {
        var author = jwsParsingService.parseAuth(request.getAuthToken(), JWT_SUBJECT);
        commentRepository.save(new Comment(null, author, request.getContent(), null, voteName, null));
    }

    @Override
    public void editComment(EditCommentRequest request) {
        var author = jwsParsingService.parseAuth(request.getAuthToken(), JWT_SUBJECT);
        commentRepository.get(request.getId())
                         .ifPresent(comment -> {
                             if (!comment.writtenBy(author)) throw new CommentFromAnotherAuthor();

                             commentRepository.update(request.getId(), request.getContent());
                         });
    }

    @Override
    public void deleteComment(DeleteCommentRequest request) throws CommentFromAnotherAuthor {
        var author = jwsParsingService.parseAuth(request.getAuthToken(), JWT_SUBJECT);
        commentRepository.get(request.getId())
                         .ifPresent(comment -> {
                             if (!comment.writtenBy(author)) throw new CommentFromAnotherAuthor();

                             commentRepository.delete(request.getId());
                         });
    }

    @Override
    public void replyOnComment(ReplyCommentRequest request) {
        var author = jwsParsingService.parseAuth(request.getAuthToken(), JWT_SUBJECT);
        commentRepository.get(request.getTargetId())
                         .ifPresent(comment -> commentRepository.save(new Comment(null,
                                                                                  author,
                                                                                  request.getContent(),
                                                                                  null,
                                                                                  comment.voteName(),
                                                                                  request.getTargetId())));
    }

    @Override
    public void cast(String voteName, CastVoteRequest castVote) {
        var voter = jwsParsingService.parseAuth(castVote.getAuthToken(), JWT_SUBJECT);
        voteRepository.findByName(voteName)
                      .ifPresentOrElse(
                              vote -> {
                                  try {
                                      if (voter.equals(vote.getAuthor())) {
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
        boolean succeeded = vote.succeeded();
        voteRepository.updateSuccess(voteName, succeeded);

        return succeeded;
    }
}
