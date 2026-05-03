package tech.provve.validation.service;

import tech.provve.api.server.generated.dto.Observation;

import java.nio.file.Path;

public interface ValidationService {

    /**
     * Save a completed observation for an examinee.
     */
    void observed(Observation observation);

    /**
     * Run the validation process for a solution.
     *
     * @return true if accepted <br>
     * false if accepted and vote for exam removal is <code>active</code>, that is, result of validation can be lost.
     */
    boolean validate(String examinee, String examName, Path solutionArchivePath);

}
