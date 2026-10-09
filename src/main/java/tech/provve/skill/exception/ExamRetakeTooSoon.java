package tech.provve.skill.exception;

public class ExamRetakeTooSoon extends RuntimeException {

    public ExamRetakeTooSoon(String examinee, String exam) {
        super("Exam '%s' can be retaken by '%s' no more than once a week".formatted(exam, examinee));
    }
}
