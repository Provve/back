package tech.provve.task;

import com.github.kagkarlsson.scheduler.task.TaskDescriptor;
import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import tech.provve.accounts.Account;

public class DowngradePremiumAccountTask {

    public static final TaskDescriptor<Void> DESCRIPTOR = TaskDescriptor.of("DOWNGRADE_PREMIUM_ACCOUNT");
    static final OneTimeTask<Void> TASK = Tasks.oneTime(DESCRIPTOR)
                                               .onFailureRetryLater()
                                               .execute((task, _) -> Account.downgrade(task.getId()));
}
