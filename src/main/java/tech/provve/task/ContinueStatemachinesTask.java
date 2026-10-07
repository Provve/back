package tech.provve.task;

import com.github.kagkarlsson.scheduler.task.TaskDescriptor;
import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import tech.provve.statemachine.domain.Statemachine;

public class ContinueStatemachinesTask {

    public static final TaskDescriptor<Void> DESCRIPTOR = TaskDescriptor.of("CONTINUE_STATEMACHINES");
    static final OneTimeTask<Void> TASK = Tasks.oneTime(DESCRIPTOR)
                                               .execute((_, _) -> Statemachine.continueAll());
}
