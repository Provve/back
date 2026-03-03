package tech.provve.api.server.factory;

import io.avaje.config.Config;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Factory
public class S3Factory {

    @Bean
    public S3Client s3Client(S3Settings settings) {
        return S3Client.builder()
                       .endpointOverride(URI.create(settings.url))
                       .region(Region.of(settings.region))
                       .credentialsProvider(StaticCredentialsProvider.create(
                               AwsBasicCredentials.create(settings.accessKey, settings.secretKey)))
                       .serviceConfiguration(S3Configuration.builder()
                                                            .pathStyleAccessEnabled(true)
                                                            .build())
                       .build();
    }

    @Bean
    public S3AsyncClient s3AsyncClient(S3Settings s3Settings) {
        return S3AsyncClient.crtBuilder()
                            .endpointOverride(URI.create(s3Settings().url))
                            .region(Region.of(s3Settings.region))
                            .credentialsProvider(StaticCredentialsProvider.create(
                                    AwsBasicCredentials.create(s3Settings.accessKey, s3Settings.secretKey)))
                            .retryConfiguration(builder -> builder.numRetries(5))
                            .build();
    }

    @Bean
    public S3Settings s3Settings() {
        return new S3Settings(
                Config.get("s3.credentials.url"),
                Config.get("s3.credentials.region"),
                Config.get("s3.credentials.access-key"),
                Config.get("s3.credentials.secret-key")
        );
    }

    public record S3Settings(String url, String region, String accessKey, String secretKey) {

    }

}
