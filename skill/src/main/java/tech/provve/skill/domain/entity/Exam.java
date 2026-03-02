package tech.provve.skill.domain.entity;

import org.jspecify.annotations.NullMarked;

/**
 * Данные экзамена.
 *
 * @param name Название экзамена
 * @param skillName   Какой навык экзамен проверяет
 * @param description Постановка задания для экзаменуемых
 * @param materialUrl Ссылка на учебный материал в хранилище S3
 */
@NullMarked
public record Exam(
        String name,
        String skillName,
        String description,
        String materialUrl
) {

}
