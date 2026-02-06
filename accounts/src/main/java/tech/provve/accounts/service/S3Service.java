package tech.provve.accounts.service;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.UUID;

@Singleton
public class S3Service {

    private static final String PERMANENT_URL_FORMAT = "%s/%s";

    @External
    private final S3Client s3Client;

    public S3Service(S3Client client) {
        s3Client = client;
    }

    /**
     * Checks if the specified bucket exists. Amazon S3 buckets are named in a global namespace; use this method to
     * determine if a specified bucket name already exists, and therefore can't be used to create a new bucket.
     *
     * @param bucketName The name of the bucket to check.
     * @return true if bucket doesn't exist <br>
     * false if the bucket exists.
     */
    public boolean bucketUnexists(String bucketName) {
        try {
            s3Client.getBucketAcl(r -> r.bucket(bucketName));
            return false;
        } catch (AwsServiceException ase) {
            // A redirect error or an AccessDenied exception means the bucket exists but it's not in this region
            // or we don't have permissions to it.
            if ((ase.statusCode() == HttpStatusCode.MOVED_PERMANENTLY) || "AccessDenied".equals(ase.awsErrorDetails()
                                                                                                   .errorCode())) {
                return false;
            }
            if (ase.statusCode() == HttpStatusCode.NOT_FOUND) {
                return true;
            }
            throw ase;
        }
    }

    /**
     * Загружает объект в S3 и возвращает постоянную ссылку в формате "bucket/obj-key"
     */
    public String uploadToS3(String bucket, byte[] bytes) {
        var key = UUID.nameUUIDFromBytes(bytes)
                      .toString();

        s3Client.putObject(
                b -> b.bucket(bucket)
                      .key(key), RequestBody.fromBytes(bytes)
        );
        return PERMANENT_URL_FORMAT.formatted(bucket, key);
    }
}
