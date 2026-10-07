package tech.provve.accounts.exception;

public class AccountAlreadyExists extends RuntimeException {

    public AccountAlreadyExists(String message) {
        super(message);
    }
}
