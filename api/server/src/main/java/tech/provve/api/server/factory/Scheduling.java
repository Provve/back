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
import tech.provve.accounts.service.S3Service;
import tech.provve.accounts.service.application.AccountService;

import javax.sql.DataSource;
import java.util.List;

import static java.time.Duration.ofMinutes;
import static tech.provve.libs.scheduling.Descriptors.DOWNGRADE_PREMIUM_ACCOUNT;
import static tech.provve.libs.scheduling.Descriptors.INIT_S3_BUCKETS;

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
    public Scheduler scheduler(DataSource dataSource, List<Task<?>> tasksDefinitions) {
        return Scheduler.create(dataSource, tasksDefinitions)
                        .pollUsingLockAndFetch(0.5, 1.0)
                        .pollingInterval(ofMinutes(1))
                        .registerShutdownHook()
                        .threads(5)
                        .build();
    }

}
