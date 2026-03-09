package tech.provve.statemachine.exception;

public class StatemachineAlreadyExists extends RuntimeException {

    public StatemachineAlreadyExists(String name) {
        super("Statemachine '%s' already exists.".formatted(name));
    }
}
