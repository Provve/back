package tech.provve.skill.exception;

public class SkillAlreadyExists extends RuntimeException {

    public SkillAlreadyExists(String name) {
        super("Skill '%s' already exists".formatted(name));
    }
}
