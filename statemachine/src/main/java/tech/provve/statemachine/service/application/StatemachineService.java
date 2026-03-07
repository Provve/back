package tech.provve.statemachine.service.application;

import tech.provve.statemachine.exception.StatemachineAlreadyExists;

public interface StatemachineService {

    /**
     * Continue to process state machines after shutdown
     */
    void continueAll();

    void createSaveExam(String name, String author, String delayedVoteJson) throws StatemachineAlreadyExists;

    void createCheckSolution(String name, String examinee) throws StatemachineAlreadyExists;

}
