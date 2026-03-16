package tech.provve.api.server.factory;

import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.task.Task;
import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import io.avaje.config.Config;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import jakarta.inject.Named;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketCannedACL;
import tech.provve.accounts.service.application.AccountService;
import tech.provve.skill.repository.ExamRepository;
import tech.provve.skill.repository.SkillRepository;
import tech.provve.skill.repository.VoteRepository;
import tech.provve.skill.service.application.SkillService;
import tech.provve.skill.service.application.VoteService;
import tech.provve.statemachine.service.domain.StatemachineService;
import terch.provve.libs.s3.S3Service;

import javax.sql.DataSource;
import java.util.List;

import static java.time.Duration.ofMinutes;
import static tech.provve.libs.scheduling.Descriptors.*;

/**
 * ID каждой таски = vote name
 */
@Factory
public class Scheduling {

    @Bean
    @Named("1")
    public OneTimeTask<Void> initBuckets(S3Client s3Client, S3Service s3Service) {
        String imagesBucket = Config.get("s3.buckets.images");
        String examsBucket = Config.get("s3.buckets.exams");
        String solutionsBucket = Config.get("s3.buckets.solutions");

        return Tasks.oneTime(INIT_S3_BUCKETS)
                    .execute(((_, _) -> {
                        if (s3Service.bucketUnexists(imagesBucket)) {
                            s3Client.createBucket(b -> b.bucket(imagesBucket)
                                                        .acl(BucketCannedACL.PUBLIC_READ));
                        }
                        if (s3Service.bucketUnexists(examsBucket)) {
                            s3Client.createBucket(b -> b.bucket(examsBucket));
                        }
                        if (s3Service.bucketUnexists(solutionsBucket)) {
                            s3Client.createBucket(b -> b.bucket(solutionsBucket));
                        }
                    }));
    }

    @Bean
    @Named("2")
    public OneTimeTask<Void> downgradePremiumAccount(AccountService accountService) {
        return Tasks.oneTime(DOWNGRADE_PREMIUM_ACCOUNT)
                    .onFailureRetryLater()
                    .execute((task, _) -> accountService.downgrade(task.getId()));
    }

    @Bean
    @Named("3")
    public OneTimeTask<Void> addSkillAfterVote(SkillService skillService, VoteService voteService, VoteRepository voteRepository) {
        return Tasks.oneTime(ADD_SKILL_AFTER_VOTE)
                    .execute((task, _) -> {
                        boolean success = voteService.end(task.getId());
                        if (success) {
                            voteRepository.findByName(task.getId())
                                          .ifPresent(skillService::create);
                        }
                    });
    }

    @Bean
    @Named("4")
    public OneTimeTask<Void> deleteSkillAfterVote(VoteService voteService, SkillRepository skillRepository) {
        return Tasks.oneTime(DELETE_SKILL_AFTER_VOTE)
                    .execute((task, _) -> {
                        boolean success = voteService.end(task.getId());
                        if (success) skillRepository.delete(task.getId());
                    });
    }

    @Bean
    @Named("5")
    @SuppressWarnings("all")
    public OneTimeTask<Void> addExamAfterVote(VoteService voteService, VoteRepository voteRepository, ExamRepository examRepository) {
        return Tasks.oneTime(ADD_EXAM_AFTER_VOTE)
                    .execute((task, _) -> {
                        boolean success = voteService.end(task.getId());
                        if (success) {
                            voteRepository.findByName(task.getId())
                                          .ifPresent(vote -> examRepository.save(vote.getExam()));
                        }
                    });
    }

    @Bean
    @Named("6")
    @SuppressWarnings("all")
    public OneTimeTask<Void> continueStateMachines(StatemachineService statemachineService) {
        return Tasks.oneTime(CONTINUE_STATEMACHINES)
                    .execute((_, _) -> {
                        statemachineService.continueAll();
                    });
    }

    @Bean
    public Scheduler scheduler(DataSource dataSource, List<Task<?>> tasksDefinitions) {
        return Scheduler.create(dataSource, tasksDefinitions)
                        .pollUsingLockAndFetch(0.5, 1.0)
                        .pollingInterval(ofMinutes(1))
                        .registerShutdownHook()
                        .threads(5)
                        .build();
    }

}
