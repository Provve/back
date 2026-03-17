package tech.provve.skill.service.application;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.api.server.generated.dto.CreateSessionRequest;
import tech.provve.api.server.generated.dto.CreateSessionResponse;
import tech.provve.skill.exception.ExamPassTwice;
import tech.provve.skill.repository.ResultRepository;
import tech.provve.skill.repository.SessionRepository;
import tech.provve.skill.repository.VoteRepository;

import java.security.SecureRandom;

import static tech.provve.accounts.service.JwsParsingService.JWT_SUBJECT;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SessionServiceImpl implements SessionService {

    private final ResultRepository resultRepository;
    private final SessionRepository sessionRepository;
    private final VoteRepository voteRepository;
    private final JwsParsingService jwsParsingService;

    @Override
    @SneakyThrows
    public CreateSessionResponse create(CreateSessionRequest request) throws ExamPassTwice {
        var login = jwsParsingService.parseAuth(request.getAuthToken(),
                                                JWT_SUBJECT);
        boolean notFirstAttempt = sessionRepository.exists(login) || resultRepository.exists(login);
        if (notFirstAttempt) throw new ExamPassTwice(login,
                                                     request.getExamName());

        boolean skillCanBeRemoved = voteRepository.exists(request.getExamName());
        var nonce = String.valueOf(SecureRandom.getInstanceStrong()
                                               .nextInt());
        return new CreateSessionResponse(true,
                                         request.getRedirect(),
                                         skillCanBeRemoved,
                                         nonce);
    }
}
