package tech.provve.skill.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
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
 */
@Getter
@Builder(toBuilder = true)
public class Vote {

    /**
     * Название голосования. Оно же и ID объекта голосования.
     */
    @NonNull
    private final String name;

    /**
     * Признак активности голосования.
     */
    private final boolean active;

    /**
     * Признак успешного завершения голосования.
     */
    private final boolean success;

    /**
     * Автор голосования.
     */
    @NonNull
    private final String author;

    /**
     * Конечный срок, когда голосование закроется, будет подсчитан результат и совершено действие.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime deadline;

    /**
     * Аргументы за совершение действия, предложенного в голосовании
     */
    @NonNull
    private final String arguments;

    /**
     * Тип голосования
     */
    @NonNull
    private final Type type;

    /**
     * Теги голосования
     */
    @NonNull
    private final List<String> tags;

    /**
     * Дополнительная информация для типа {@link Type#ADD_EXAM}.
     */
    @Nullable
    private final Exam exam;

    /**
     * Реакции на голосование.
     */
    @Nullable
    private final VoteReactions reactions;

    public enum Type {
        /**
         * Добавление навыка.
         */
        ADD_SKILL(0),

        /**
         * Удаление навыка.
         */
        DEL_SKILL(1),

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

    /**
     * Положительных голосов больше, т. е. голосование успешно?
     */
    public boolean succeeded() {
        int negative = 0 == reactions.negative()
                       ? 1
                       : reactions.negative();
        int positiveRelation = reactions.positive() / negative;
        return positiveRelation >= 1;
    }

}
