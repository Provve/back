package tech.provve.skill.exception;

public class SkillNotFound extends RuntimeException {

    public SkillNotFound(String name) {
        super("Skill '%s' not found".formatted(name));
    }
}
