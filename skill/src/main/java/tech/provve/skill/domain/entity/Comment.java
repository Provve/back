package tech.provve.skill.domain.entity;

import org.jspecify.annotations.NullUnmarked;

import java.time.LocalDateTime;

/**
 * Комментарий к голосованию.
 *
 * @param id       уникальный идентификатор комментария. Ставится в БД.
 * @param author   автор комментария
 * @param content  содержание комментария
 * @param created  время создания комментария
 * @param voteName связанное голосование
 * @param replyFor В ответ на какой комментарий написан этот?
 */
public record Comment(@NullUnmarked Integer id, String author, String content, LocalDateTime created, String voteName, Integer replyFor) {

}
