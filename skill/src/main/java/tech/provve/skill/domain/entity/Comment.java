package tech.provve.skill.domain.entity;

import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Комментарий к голосованию.
 *
 * @param id       уникальный идентификатор комментария. Ставится в БД.
 * @param author   автор комментария
 * @param content  содержание комментария
 * @param created  время создания комментария. Ставится в БД
 * @param voteName связанное голосование
 * @param parentId В ответ на какой комментарий написан этот?
 */
public record Comment(@Nullable Integer id, String author, String content, LocalDateTime created, String voteName, @Nullable Integer parentId) {

    public boolean writtenBy(String author) {
        return Objects.equals(this.author, author);
    }
}