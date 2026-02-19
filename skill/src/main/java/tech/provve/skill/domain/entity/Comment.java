package tech.provve.skill.domain.entity;

import java.time.LocalDateTime;

/**
 * Комментарий к голосованию.
 *
 * @param id       уникальный идентификатор комментария
 * @param author   автор комментария
 * @param content  содержание комментария
 * @param created  время создания комментария
 * @param voteName связанное голосование
 */
public record Comment(int id, String author, String content, LocalDateTime created, String voteName) {

}
