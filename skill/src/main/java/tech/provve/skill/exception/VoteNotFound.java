package tech.provve.skill.exception;

public class VoteNotFound extends RuntimeException {

    public VoteNotFound(String name) {
        super("Vote '%s' not found".formatted(name));
    }
}
