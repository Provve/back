package tech.provve.task;

import com.github.kagkarlsson.scheduler.task.TaskDescriptor;
import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import tech.provve.skill.domain.Vote;
import tech.provve.util.Storage;

public class ArchiveSkillAfterVoteTask {

    public static final TaskDescriptor<Void> DESCRIPTOR = TaskDescriptor.of("SKILL_ARCHIVE");
    static final OneTimeTask<Void> TASK = Tasks.oneTime(DESCRIPTOR)
                                               .execute((task, _) -> {
                                                   boolean success = Vote.end(task.getId());
                                                   if (success) {
                                                       Storage.archiveSkill(task.getId());
                                                   }
                                               });
}
