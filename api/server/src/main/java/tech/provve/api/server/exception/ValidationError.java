package tech.provve.api.server.exception;

public class ValidationError extends RuntimeException {

    public ValidationError(String message) {
        super(message);
    }
}
