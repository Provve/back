package tech.provve.skill.exception;

public class ExamAlreadyExists extends RuntimeException {

    public ExamAlreadyExists(String name) {
        super("Exam '%s' already exists".formatted(name));
    }
}
