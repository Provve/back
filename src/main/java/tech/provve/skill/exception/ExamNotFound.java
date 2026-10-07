package tech.provve.skill.exception;

public class ExamNotFound extends RuntimeException {

    public ExamNotFound(String name) {
        super("Exam '%s' not found".formatted(name));
    }
}
