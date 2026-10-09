package tech.provve.util;

import com.uwyn.urlencoder.UrlEncoder;
import io.avaje.config.Config;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;
import java.util.UUID;


public class S3 {

    private static final String PERMANENT_URL_FORMAT = "%s/%s";
    private static final String S3_URL = Config.get("s3.credentials.url");
    private static final String S3_REGION = Config.get("s3.credentials.region");
    private static final String S3_ACCESS_KEY = Config.get("s3.credentials.access-key");
    private static final String S3_SECRET_KEY = Config.get("s3.credentials.secret-key");

    public static final S3Client S3_CLIENT = S3Client.builder()
                                                     .endpointOverride(URI.create(S3_URL))
                                                     .region(software.amazon.awssdk.regions.Region.of(S3_REGION))
                                                     .credentialsProvider(StaticCredentialsProvider.create(
                                                             AwsBasicCredentials.create(S3_ACCESS_KEY, S3_SECRET_KEY)))
                                                     .serviceConfiguration(S3Configuration.builder()
                                                                                          .pathStyleAccessEnabled(true)
                                                                                          .build())
                                                     .build();

    public static final S3AsyncClient S3_ASYNC_CLIENT = S3AsyncClient.crtBuilder()
                                                                     .endpointOverride(URI.create(S3_URL))
                                                                     .region(software.amazon.awssdk.regions.Region.of(S3_REGION))
                                                                     .credentialsProvider(StaticCredentialsProvider.create(
                                                                             AwsBasicCredentials.create(S3_ACCESS_KEY, S3_SECRET_KEY)))
                                                                     .retryConfiguration(builder -> builder.numRetries(5))
                                                                     .build();

    /**
     * Checks if the specified bucket exists. Amazon S3 buckets are named in a global namespace; use this method to
     * determine if a specified bucket name already exists, and therefore can't be used to create a new bucket.
     *
     * @param bucketName The name of the bucket to check.
     * @return true if bucket doesn't exist <br>
     * false if the bucket exists.
     */
    public static boolean bucketUnexists(String bucketName) {
        try {
            S3_CLIENT.getBucketAcl(r -> r.bucket(bucketName));
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
     * Upload sync
     *
     * @return постоянная ссылка в формате "bucket/obj-key"
     */
    public static String upload(String bucket, String key, byte[] bytes) {
        S3_CLIENT.putObject(
                b -> b.bucket(bucket)
                      .key(key), RequestBody.fromBytes(bytes)
        );
        return PERMANENT_URL_FORMAT.formatted(bucket, key);
    }

    /**
     * Upload async using CRT
     *
     * @return постоянная ссылка в формате "bucket/obj-key"
     */
    public static String crtUpload(String bucket, String key, byte[] body) {
        S3_ASYNC_CLIENT.putObject(
                b -> b.bucket(bucket)
                      .key(key), AsyncRequestBody.fromBytes(body)
        );
        return PERMANENT_URL_FORMAT.formatted(bucket, key);
    }

    public static byte[] download(String bucket, String key) {
        return S3_CLIENT.getObjectAsBytes(builder -> builder
                                .bucket(bucket)
                                .key(key)
                                .build())
                        .asByteArray();
    }

    public static void delete(String bucket, String key) {
        S3_CLIENT.deleteObject(builder -> builder.bucket(bucket)
                                                 .key(key)
                                                 .build());
    }

    public static class Key {

        /**
         * Генерирует ключ приватного архива навыка (проверяющей части)
         *
         * @param skillName название самого навыка
         */
        public static String privateArchive(String skillName) {
            return "private/" +
                    UrlEncoder.encode(skillName);
        }

        /**
         * Генерирует ключ публичного архива навыка (проверяемой части)
         * <br> skillName — название самого навыка
         */
        public static String publicArchive(String skillName) {
            return "public/" +
                    UrlEncoder.encode(skillName);
        }

        public static String solutionArchive(String skillName, String examinee) {
            return examinee + "/" + UrlEncoder.encode(skillName);
        }

        /**
         * Генерирует ключ для объединенного архива с проверяющей и проверяемой частью, содержащую решение от пользователя
         */
        public static String mergedArchive(String skillName, String examinee) {
            return "merged/" +
                    examinee + "/" + UrlEncoder.encode(skillName);
        }

        public static String uuid(byte[] data) {
            return UUID.nameUUIDFromBytes(data)
                       .toString();
        }
    }
}
