package tech.provve.skill.service.domain;

import tech.provve.api.server.generated.dto.CollectionAuthenticatedRequest;
import tech.provve.api.server.generated.dto.Results;

public interface ResultService {

    Results list(CollectionAuthenticatedRequest request);

}
