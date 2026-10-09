package tech.provve.task;

import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.task.SchedulableInstance;

import javax.sql.DataSource;
import java.time.Instant;
import java.util.List;

import static java.time.Duration.ofMinutes;

/**
 * Планировщик задач, которые продолжают выполняться даже после перезагрузки.
 */
public class Scheduling {

    private static Scheduler scheduler;

    public static void init(DataSource dataSource) {
        scheduler = Scheduler.create(
                                     dataSource,
                                     InitS3BucketsTask.TASK,
                                     ContinueStatemachinesTask.TASK,
                                     DowngradePremiumAccountTask.TASK,
                                     AddSkillAfterVoteTask.TASK,
                                     ArchiveSkillAfterVoteTask.TASK
                             )
                             .pollUsingLockAndFetch(0.5, 1.0)
                             .pollingInterval(ofMinutes(1))
                             .registerShutdownHook()
                             .threads(5)
                             .build();
        scheduler.start();
        scheduler.schedule(InitS3BucketsTask.DESCRIPTOR.instance("1")
                                                       .scheduledTo(Instant.now()));
        scheduler.schedule(ContinueStatemachinesTask.DESCRIPTOR.instance("1")
                                                               .scheduledTo(Instant.now()));
    }

    public static void addSkill(String voteName, Instant when) {
        scheduler.schedule(AddSkillAfterVoteTask.DESCRIPTOR.instance(voteName)
                                                           .scheduledTo(when));
    }

    public static void archiveSkill(String voteName, Instant when) {
        scheduler.schedule(ArchiveSkillAfterVoteTask.DESCRIPTOR.instance(voteName)
                                                               .scheduledTo(when));
    }

    public static void downgradePremiumAccount(String login, Instant when) {
        scheduler.schedule(DowngradePremiumAccountTask.DESCRIPTOR.instance(login)
                                                                 .scheduledTo(when));
    }

    /**
     * Schedule batch
     *
     * @param tasks List of {@link SchedulableInstance}
     */
    @SuppressWarnings("all")
    public static void schedule(List<SchedulableInstance<?>> tasks) {
        scheduler.scheduleBatch(tasks);
    }

    public static void shutdown() {
        scheduler.stop();
    }
}
