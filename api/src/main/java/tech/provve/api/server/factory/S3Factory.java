package tech.provve.api.server.factory;

import io.avaje.config.Config;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Factory
public class S3Factory {

    @Bean
    public S3Client s3Client() {
        var url = Config.get("s3.url");
        var region = Config.get("s3.region");
        var keyId = Config.get("s3.access-key");
        var secretKey = Config.get("s3.secret-key");

        return S3Client.builder()
                       .endpointOverride(URI.create(url))
                       .region(Region.of(region))
                       .credentialsProvider(StaticCredentialsProvider.create(
                               AwsBasicCredentials.create(keyId, secretKey)))
                       .serviceConfiguration(S3Configuration.builder()
                                                            .pathStyleAccessEnabled(true)
                                                            .build())
                       .build();
    }

}
