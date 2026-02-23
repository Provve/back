package tech.provve.skill.domain.entity;

import org.jspecify.annotations.NullUnmarked;

/**
 * Голосование на добавление экзамена.
 *
 * @param skillName   Имя навыка, к которому добавляется экзамен
 * @param description Финальная постановка задания для экзаменуемых
 * @param materialUrl Ссылка на учебный материал в хранилище S3
 */
@NullUnmarked
public record ExamAddVote(
        String skillName,
        String description,
        String materialUrl
) {

}
