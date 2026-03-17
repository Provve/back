package tech.provve.skill.service.application;

import tech.provve.api.server.generated.dto.CreateSessionRequest;
import tech.provve.api.server.generated.dto.CreateSessionResponse;
import tech.provve.skill.exception.ExamPassTwice;

public interface SessionService {

    CreateSessionResponse create(CreateSessionRequest request) throws ExamPassTwice;

}
