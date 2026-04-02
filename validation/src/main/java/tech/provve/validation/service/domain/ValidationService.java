package tech.provve.validation.service.domain;

import tech.provve.api.server.generated.dto.Observation;

public interface ValidationService {

    /**
     * Save a completed observation for an examinee.
     */
    void observed(Observation observation);

}
