package tech.provve.skill.service.application;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import tech.provve.api.server.generated.dto.CreateSessionRequest;
import tech.provve.api.server.generated.dto.CreateSessionResponse;
import tech.provve.libs.auth.JwsParsingService;
import tech.provve.skill.domain.entity.Session;
import tech.provve.skill.exception.ExamNotFound;
import tech.provve.skill.exception.ExamPassTwice;
import tech.provve.skill.repository.ExamRepository;
import tech.provve.skill.repository.ResultRepository;
import tech.provve.skill.repository.SessionRepository;
import tech.provve.skill.repository.VoteRepository;

import java.security.SecureRandom;
import java.time.Instant;

import static tech.provve.libs.auth.JwsParsingService.JWT_SUBJECT;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SessionServiceImpl implements SessionService {

    private final ResultRepository resultRepository;
    private final SessionRepository sessionRepository;
    private final ExamRepository examRepository;
    private final VoteRepository voteRepository;
    private final JwsParsingService jwsParsingService;

    @Override
    @SneakyThrows
    public CreateSessionResponse create(CreateSessionRequest request) {
        if (!(examRepository.exists(request.getExamName()))) {
            throw new ExamNotFound(request.getExamName());
        }

        var login = jwsParsingService.parseAuth(request.getAuthToken(),
                                                JWT_SUBJECT);
        boolean notFirstAttempt = sessionRepository.exists(login) || resultRepository.exists(login);
        if (notFirstAttempt) throw new ExamPassTwice(login,
                                                     request.getExamName());

        boolean skillCanBeRemoved = voteRepository.exists(request.getExamName(), true);
        var nonce = String.valueOf(SecureRandom.getInstanceStrong()
                                               .nextInt());

        sessionRepository.save(new Session(login, request.getExamName(), Instant.now()));

        return new CreateSessionResponse(request.getRedirect(),
                                         skillCanBeRemoved,
                                         nonce);
    }
}
