package tech.provve.task;

import alekseyvideman.dop.Collection;
import com.github.kagkarlsson.scheduler.task.TaskDescriptor;
import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import tech.provve.accounts.Account;
import tech.provve.constants.Entity;
import tech.provve.skill.domain.Vote;
import tech.provve.util.Storage;

public class AddExamAfterVoteTask {

    public static final TaskDescriptor<String> DESCRIPTOR = TaskDescriptor.of("ADD_EXAM_AFTER_VOTE", String.class);
    static final OneTimeTask<String> TASK = Tasks.oneTime(DESCRIPTOR)
                                                 .execute((task, _) -> {
                                                     boolean success = Vote.end(task.getId());
                                                     if (success) {
                                                         Storage.findVoteByName(task.getId())
                                                                .ifPresent(vote -> Storage.saveExam(Collection.get(vote, Entity.Vote.EXAM)));
                                                         Account.notifyVoteStarted(task.getId(), task.getData());
                                                     }
                                                 });
}
