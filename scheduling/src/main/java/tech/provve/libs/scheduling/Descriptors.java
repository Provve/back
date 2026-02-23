package tech.provve.libs.scheduling;

import com.github.kagkarlsson.scheduler.task.TaskDescriptor;

public final class Descriptors {

    private Descriptors() {
        /* This utility class should not be instantiated */
    }

    public static final TaskDescriptor<Void> INIT_S3_BUCKETS = TaskDescriptor.of("INIT_S3_BUCKETS");
    public static final TaskDescriptor<Void> DOWNGRADE_PREMIUM_ACCOUNT = TaskDescriptor.of("DOWNGRADE_PREMIUM_ACCOUNT");

    /**
     * Действие выполняемое по окончанию голосования за добавление навыка
     */
    public static final TaskDescriptor<Void> SKILL_ADD = TaskDescriptor.of("SKILL_ADD");

    /**
     * Действие выполняемое по окончанию голосования за удаление навыка
     */
    public static final TaskDescriptor<Void> SKILL_DELETE = TaskDescriptor.of("SKILL_DELETE");

    /**
     * Действие выполняемое по окончанию голосования за добавление экзамена к навыку
     */
    public static final TaskDescriptor<Void> EXAM_ADD = TaskDescriptor.of("EXAM_ADD");

}
