package tech.provve.skill.domain.entity;

import java.time.Duration;

/**
 * @param examName Название экзамена
 * @param examinee Логин экзаменуемого
 * @param duration Время прохождения экзамена
 */
public record ExamResult(
        String examName,
        String examinee,
        Duration duration
) {

}
