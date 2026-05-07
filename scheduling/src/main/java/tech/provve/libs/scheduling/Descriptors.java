package tech.provve.libs.scheduling;

import com.github.kagkarlsson.scheduler.task.TaskDescriptor;

public final class Descriptors {

    private Descriptors() {
        /* This utility class should not be instantiated */
    }

    public static final TaskDescriptor<Void> INIT_S3_BUCKETS = TaskDescriptor.of("INIT_S3_BUCKETS");
    public static final TaskDescriptor<Void> CONTINUE_STATEMACHINES = TaskDescriptor.of("CONTINUE_STATEMACHINES");
    public static final TaskDescriptor<Void> DOWNGRADE_PREMIUM_ACCOUNT = TaskDescriptor.of("DOWNGRADE_PREMIUM_ACCOUNT");

    /**
     * Действие выполняемое по окончанию голосования за добавление навыка
     */
    public static final TaskDescriptor<Void> ADD_SKILL_AFTER_VOTE = TaskDescriptor.of("ADD_SKILL_AFTER_VOTE");

    /**
     * Действие выполняемое по окончанию голосования за удаление навыка
     */
    public static final TaskDescriptor<Void> DELETE_SKILL_AFTER_VOTE = TaskDescriptor.of("SKILL_DELETE");

    /**
     * Действие выполняемое по окончанию голосования за добавление экзамена к навыку. <br>
     * data — название навыка (skill.name)
     */
    public static final TaskDescriptor<String> ADD_EXAM_AFTER_VOTE = TaskDescriptor.of("ADD_EXAM_AFTER_VOTE", String.class);

}
