package tech.provve.accounts.exception;

public class AccountAlreadyUpgraded extends RuntimeException {

    public AccountAlreadyUpgraded(String message) {
        super(message);
    }
}
