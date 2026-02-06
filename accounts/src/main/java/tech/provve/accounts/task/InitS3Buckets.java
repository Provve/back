package tech.provve.accounts.task;

import io.avaje.config.Config;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketCannedACL;
import tech.provve.accounts.service.S3Service;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class InitS3Buckets implements Runnable {

    private final S3Client s3Client;

    private final S3Service s3Service;

    private static final String IMAGES_BUCKET = Config.get("s3.buckets.images");

    private static final String EXAMS_BUCKET = Config.get("s3.buckets.exams");

    private static final String SOLUTIONS_BUCKET = Config.get("s3.buckets.solutions");

    @Override
    public void run() {
        if (s3Service.bucketUnexists(IMAGES_BUCKET)) {
            s3Client.createBucket(b -> b.bucket(IMAGES_BUCKET)
                                        .acl(BucketCannedACL.PUBLIC_READ));
        }
        if (s3Service.bucketUnexists(EXAMS_BUCKET)) {
            s3Client.createBucket(b -> b.bucket(EXAMS_BUCKET));
        }
        if (s3Service.bucketUnexists(SOLUTIONS_BUCKET)) {
            s3Client.createBucket(b -> b.bucket(SOLUTIONS_BUCKET));
        }
    }
}
