package tech.provve.accounts.exception;

public class AccountNotFound extends RuntimeException {

    public AccountNotFound(String message) {
        super(message);
    }
}
