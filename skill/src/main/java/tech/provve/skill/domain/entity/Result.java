package tech.provve.skill.domain.entity;

import java.time.Duration;

/**
 * @param examName Название экзамена
 * @param examinee Логин экзаменуемого
 * @param durationMinutes Время прохождения экзамена = <i>время окончания сессии</i> - <i>время начала сессии</i>
 */
public record Result(
        String examName,
        String examinee,
        Duration durationMinutes
) {

}
