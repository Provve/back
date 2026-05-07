package tech.provve.libs.scheduling;

import com.github.kagkarlsson.scheduler.Scheduler;
import io.avaje.inject.BeanScope;
import io.avaje.inject.External;
import io.avaje.inject.PostConstruct;
import jakarta.inject.Singleton;

import java.time.Instant;

import static tech.provve.libs.scheduling.Descriptors.*;

/**
 * Планировщик задач, которые продолжают выполняться даже после перезагрузки.
 */
@Singleton
public class Scheduling {

    private final Scheduler scheduler;

    public Scheduling(@External Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void start(BeanScope beanScope) {
        scheduler.start();
        scheduler.schedule(INIT_S3_BUCKETS.instance("1")
                                          .scheduledTo(Instant.now()));
        scheduler.schedule(CONTINUE_STATEMACHINES.instance("1")
                                                 .scheduledTo(Instant.now()));
    }

    public void addSkill(String voteName, Instant when) {
        scheduler.schedule(ADD_SKILL_AFTER_VOTE.instance(voteName)
                                               .scheduledTo(when));
    }

    public void delSkill(String voteName, Instant when) {
        scheduler.schedule(DELETE_SKILL_AFTER_VOTE.instance(voteName)
                                                  .scheduledTo(when));
    }

    public void addExam(String voteName, String skillName, Instant when) {
        scheduler.schedule(ADD_EXAM_AFTER_VOTE.instance(voteName)
                                              .data(skillName)
                                              .scheduledTo(when));
    }

    public void downgradePremiumAccount(String login, Instant when) {
        scheduler.schedule(DOWNGRADE_PREMIUM_ACCOUNT.instance(login)
                                                    .scheduledTo(when));
    }

}
