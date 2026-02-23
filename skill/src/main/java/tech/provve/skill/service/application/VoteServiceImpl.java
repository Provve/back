package tech.provve.skill.service.application;

import io.avaje.inject.External;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.api.server.generated.dto.ExamAddVote;
import tech.provve.api.server.generated.dto.SkillAddVote;
import tech.provve.api.server.generated.dto.SkillDelVote;
import tech.provve.skill.domain.entity.Vote;
import tech.provve.skill.exception.SkillAlreadyExists;
import tech.provve.skill.exception.VoteAlreadyExists;
import tech.provve.skill.repository.SkillRepository;
import tech.provve.skill.repository.VoteRepository;
import tech.provve.skill.service.SanitizingService;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import static tech.provve.accounts.service.JwsParsingService.JWT_SUBJECT;
import static tech.provve.skill.service.SanitizingService.sanitize;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final SkillRepository skillRepository;

    @External
    private final JwsParsingService jwsParsingService;

    private final Supplier<LocalDateTime> deadlineSupplier;

    @Override
    public void create(SkillAddVote skillAddVote) throws VoteAlreadyExists {
        voteRepository.findByName(skillAddVote.getName())
                      .ifPresent(_ -> {
                          throw new VoteAlreadyExists(skillAddVote.getName());
                      });
        skillRepository.findByName(skillAddVote.getName())
                       .ifPresent(_ -> {
                           throw new SkillAlreadyExists(skillAddVote.getName());
                       });

        var jwtPayload = jwsParsingService.parseAuth(skillAddVote.getAuthToken());
        var author = ((String) jwtPayload.get(JWT_SUBJECT));
        var vote = new Vote(
                sanitize(skillAddVote.getName()),
                true,
                false,
                author,
                deadlineSupplier.get(),
                sanitize(skillAddVote.getArguments()),
                Vote.Type.ADD_SKILL,
                skillAddVote.getTags()
                            .stream()
                            .map(SanitizingService::sanitize)
                            .toList(),
                null,
                null
        );
        voteRepository.save(vote);
    }

    @Override
    public void create(SkillDelVote skillDelVote) throws VoteAlreadyExists {

    }

    @Override
    public void create(ExamAddVote examAddVote) throws VoteAlreadyExists {

    }
}
