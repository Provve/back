package terch.provve.libs.s3;

import com.uwyn.urlencoder.UrlEncoder;
import io.avaje.inject.External;
import jakarta.inject.Singleton;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

import java.nio.file.Path;
import java.util.UUID;

@Singleton
public class S3Service {

    private static final String PERMANENT_URL_FORMAT = "%s/%s";

    /**
     * Генерирует ключ приватного архива экзамена (проверяющей части)
     *
     * @param examName название самого экзамена
     */
    public static String privateArchiveKeygen(String examName) {
        return "private/" +
                UrlEncoder.encode(examName);
    }

    /**
     * Генерирует ключ публичного архива экзамена (проверяемой части)
     * <br> examName — название самого экзамена
     */
    public static String publicArchiveKeygen(String examName) {
        return "public/" +
                UrlEncoder.encode(examName);
    }

    /**
     * Генерирует UUID из данных
     */
    public static String defaultKeygen(byte[] data) {
        return UUID.nameUUIDFromBytes(data)
                   .toString();
    }

    private final S3Client s3Client;

    private final S3AsyncClient s3AsyncClient;

    public S3Service(@External S3Client client, @External S3AsyncClient asyncClient) {
        s3Client = client;
        s3AsyncClient = asyncClient;
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
     * Upload sync
     *
     * @return постоянная ссылка в формате "bucket/obj-key"
     */
    public String upload(String bucket, String key, byte[] bytes) {
        s3Client.putObject(
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
    public String crtUpload(String bucket, String key, Path path) {
        s3AsyncClient.putObject(
                b -> b.bucket(bucket)
                      .key(key), AsyncRequestBody.fromFile(path)
        );
        return PERMANENT_URL_FORMAT.formatted(bucket, key);
    }
}
