package tech.provve.skill;

import lombok.SneakyThrows;
import tech.provve.accounts.JwsParsing;
import tech.provve.api.generated.dto.CreateSessionRequest;
import tech.provve.api.generated.dto.CreateSessionResponse;
import tech.provve.constants.Entity;
import tech.provve.skill.exception.SkillNotFound;
import tech.provve.util.Storage;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static tech.provve.accounts.JwsParsing.JWT_SUBJECT;

public class Session {

    @SneakyThrows
    public static CreateSessionResponse create(CreateSessionRequest request) {
        if (!(Storage.skillExists(request.getSkillName()))) {
            throw new SkillNotFound(request.getSkillName());
        }

        var login = JwsParsing.parseAuth(request.getAuthToken(),
                                         JWT_SUBJECT);

        boolean skillCanBeRemoved = Storage.voteExists(request.getSkillName(), true);
        var nonce = String.valueOf(SecureRandom.getInstanceStrong()
                                               .nextInt());

        Map<String, Object> session = new HashMap<>();
        session.put(Entity.Session.OWNER, login);
        session.put(Entity.Session.SKILL_NAME, request.getSkillName());
        session.put(Entity.Session.STARTED, Instant.now());
        Storage.saveSession(session);

        return new CreateSessionResponse(request.getRedirect(),
                                         skillCanBeRemoved,
                                         nonce);
    }
}
