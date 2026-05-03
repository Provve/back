package tech.provve.api.server.factory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;

import java.time.Duration;

@Factory
public class Docker {

    @Bean
    public DockerClient dockerClient(DockerClientConfig config, DockerHttpClient httpClient) {
        return DockerClientImpl.getInstance(config, httpClient);
    }

    @Bean
    public DockerClientConfig dockerClientConfig() {
        return DefaultDockerClientConfig.createDefaultConfigBuilder()
                                        .build();
    }

    @Bean
    public DockerHttpClient dockerHttpClient(DockerClientConfig config) {
        return new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
    }

}
