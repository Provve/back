package tech.provve.statemachine.service.domain;

import tech.provve.statemachine.exception.StatemachineAlreadyExists;

import java.nio.file.Path;

public interface StatemachineService {

    /**
     * Continue to process state machines after shutdown
     */
    void continueAll();

    void createSaveExam(String name, String author, String delayedVoteJson) throws StatemachineAlreadyExists;

    void createCheckSolution(String name, String examinee, Path solutionArchivePath) throws StatemachineAlreadyExists;

}
