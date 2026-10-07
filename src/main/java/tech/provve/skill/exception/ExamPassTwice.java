package tech.provve.skill.exception;

public class ExamPassTwice extends RuntimeException {

    public ExamPassTwice(String examinee, String exam) {
        super("The user '%s' is already passing or passed exam '%s'".formatted(examinee, exam));
    }
}
