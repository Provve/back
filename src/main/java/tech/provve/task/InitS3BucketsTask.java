package tech.provve.task;

import com.github.kagkarlsson.scheduler.task.TaskDescriptor;
import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import io.avaje.config.Config;
import software.amazon.awssdk.services.s3.model.BucketCannedACL;
import tech.provve.util.S3;

public class InitS3BucketsTask {

    public static final TaskDescriptor<Void> DESCRIPTOR = TaskDescriptor.of("INIT_S3_BUCKETS");
    static final OneTimeTask<Void> TASK = Tasks.oneTime(DESCRIPTOR)
                                               .execute((_, _) -> {
                                                   String imagesBucket = Config.get("s3.buckets.images");
                                                   String examsBucket = Config.get("s3.buckets.exams");
                                                   String solutionsBucket = Config.get("s3.buckets.solutions");

                                                   if (S3.bucketUnexists(imagesBucket)) {
                                                       S3.S3_CLIENT.createBucket(b -> b.bucket(imagesBucket)
                                                                                       .acl(BucketCannedACL.PUBLIC_READ));
                                                   }
                                                   if (S3.bucketUnexists(examsBucket)) {
                                                       S3.S3_CLIENT.createBucket(b -> b.bucket(examsBucket));
                                                   }
                                                   if (S3.bucketUnexists(solutionsBucket)) {
                                                       S3.S3_CLIENT.createBucket(b -> b.bucket(solutionsBucket));
                                                   }
                                               });
}
