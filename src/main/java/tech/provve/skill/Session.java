package tech.provve.skill;

import lombok.SneakyThrows;
import tech.provve.accounts.JwsParsing;
import tech.provve.api.generated.dto.CreateSessionRequest;
import tech.provve.api.generated.dto.CreateSessionResponse;
import tech.provve.constants.Entity;
import tech.provve.skill.exception.ExamNotFound;
import tech.provve.skill.exception.ExamPassTwice;
import tech.provve.util.Storage;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static tech.provve.accounts.JwsParsing.JWT_SUBJECT;

public class Session {

    @SneakyThrows
    public static CreateSessionResponse create(CreateSessionRequest request) {
        if (!(Storage.examExists(request.getExamName()))) {
            throw new ExamNotFound(request.getExamName());
        }

        var login = JwsParsing.parseAuth(request.getAuthToken(),
                                         JWT_SUBJECT);
        boolean notFirstAttempt = Storage.sessionExists(login) || Storage.resultExists(login);
        if (notFirstAttempt) throw new ExamPassTwice(login,
                                                     request.getExamName());

        boolean skillCanBeRemoved = Storage.voteExists(request.getExamName(), true);
        var nonce = String.valueOf(SecureRandom.getInstanceStrong()
                                               .nextInt());

        Map<String, Object> session = new HashMap<>();
        session.put(Entity.Session.OWNER, login);
        session.put(Entity.Session.EXAM_NAME, request.getExamName());
        session.put(Entity.Session.STARTED, Instant.now());
        Storage.saveSession(session);

        return new CreateSessionResponse(request.getRedirect(),
                                         skillCanBeRemoved,
                                         nonce);
    }
}
