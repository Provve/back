package tech.provve.skill.service.domain;

import tech.provve.api.server.generated.dto.CollectionRequest;
import tech.provve.api.server.generated.dto.Skills;
import tech.provve.skill.domain.entity.Vote;

public interface SkillService {

    void createFrom(Vote vote);

    Skills list(CollectionRequest request);

}
