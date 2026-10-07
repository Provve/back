package tech.provve.skill.exception;

public class VoteAlreadyExists extends RuntimeException {

    public VoteAlreadyExists(String name) {
        super("Vote '%s' already exists".formatted(name));
    }
}
