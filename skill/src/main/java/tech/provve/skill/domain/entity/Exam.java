package tech.provve.skill.domain.entity;

import org.jspecify.annotations.NullMarked;

/**
 * Данные экзамена.
 *
 * @param name              Название экзамена
 * @param skillName         Какой навык экзамен проверяет
 * @param description       Постановка задания для экзаменуемых
 * @param privateArchiveUrl Проверяющая часть экзамена
 * @param publicArchiveUrl  Проверяемая часть экзамена, задание
 */
@NullMarked
public record Exam(
        String name,
        String skillName,
        String description,
        String privateArchiveUrl,
        String publicArchiveUrl
) {

}
