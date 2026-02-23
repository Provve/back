package tech.provve.skill.domain.entity;

import lombok.SneakyThrows;
import org.jspecify.annotations.Nullable;
import tech.provve.skill.domain.value.VoteReactions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Всеохватывающее представление голосования.
 * <br> ЖЗ:
 * <ol>
 *     <li>Создание</li>
 *     <li>Приём голосов</li>
 *     <li>Подсчет результата по прошествии deadline</li>
 *     <li>Завершение</li>
 * </ol>
 *
 * @param name        Название голосования. Оно же и ID объекта голосования
 * @param active      Проводится ли голосование
 * @param success     Положительно ли завершено голосование
 * @param author      Автор голосования
 * @param deadline    Конечный срок, когда голосование закроется, будет подсчитан результат и совершенно действие
 * @param arguments   Аргументы за совершение действия, предложенного в голосовании
 * @param type        Тип голосования
 * @param tags        Теги голосования
 * @param examAddVote {@link Type#ADD_EXAM}
 */
public record Vote(
        String name,
        boolean active,
        boolean success,
        String author,
        LocalDateTime deadline,
        String arguments,
        Type type,
        List<String> tags,
        @Nullable ExamAddVote examAddVote,
        @Nullable VoteReactions reactions
) {

    public enum Type {
        /**
         * Добавление навыка.
         */
        ADD_SKILL(0),

        /**
         * Удаление навыка.
         */
        DELETE_SKILL(1),

        /**
         * Добавление экзамена.
         */
        ADD_EXAM(2);

        Type(int code) {
            this.code = code;
        }

        private final int code;

        public int getCode() {
            return code;
        }

        @SneakyThrows
        public static Type map(int code) {
            return Arrays.stream(values())
                         .filter(it -> code == it.code)
                         .findFirst()
                         .orElseThrow();
        }
    }

}
