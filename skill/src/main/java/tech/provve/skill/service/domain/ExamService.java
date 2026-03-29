package tech.provve.skill.service.domain;

import tech.provve.api.server.generated.dto.CollectionRequest;
import tech.provve.api.server.generated.dto.Exams;

public interface ExamService {

    Exams list(CollectionRequest request);

}
