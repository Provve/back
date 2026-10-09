package tech.provve.util;

import alekseyvideman.dop.Collection;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.avaje.config.Config;
import lombok.SneakyThrows;
import lombok.val;
import tech.provve.constants.Entity;
import tech.provve.validation.exception.StillRunning;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Container {

    private static final DockerClientConfig DOCKER_CLIENT_CONFIG = DefaultDockerClientConfig.createDefaultConfigBuilder()
                                                                                            .build();

    private static final DockerHttpClient DOCKER_HTTP_CLIENT = new ApacheDockerHttpClient.Builder()
            .dockerHost(DOCKER_CLIENT_CONFIG.getDockerHost())
            .sslConfig(DOCKER_CLIENT_CONFIG.getSSLConfig())
            .maxConnections(100)
            .connectionTimeout(Duration.ofSeconds(30))
            .responseTimeout(Duration.ofSeconds(45))
            .build();

    private static final DockerClient DOCKER_CLIENT = DockerClientImpl.getInstance(DOCKER_CLIENT_CONFIG, DOCKER_HTTP_CLIENT);

    private static final RetryPolicy<Void> RETRY_POLICY = RetryPolicy.<Void>builder()
                                                                     .withDelay(Duration.ofSeconds(10))
                                                                     .withMaxRetries(50)
                                                                     .handle(StillRunning.class)
                                                                     .build();

    /**
     * Build image with exam + solution files and name it after an examinee
     */
    @SneakyThrows
    public static void buildDockerImage(String examinee, Path dockerFileHomeDir) {
        DOCKER_CLIENT.buildImageCmd(dockerFileHomeDir.toFile())
                     .withNetworkMode("none")
                     .withTags(Set.of(examinee))
                     .exec(new ResultCallback.Adapter<>() {
                         // ignore
                     })
                     .awaitCompletion();
    }

    /**
     * Create container from image named after an examinee. Process its whole lifecycle.
     */
    public static void processContainer(String examinee, String skillName) {
        CreateContainerResponse container = DOCKER_CLIENT.createContainerCmd(examinee)
                                                         .withNetworkDisabled(true)
                                                         .exec();
        DOCKER_CLIENT.startContainerCmd(container.getId())
                     .exec();
        Map<String, Object> containerView = new HashMap<>();
        containerView.put(Entity.Container.EXAMINEE, examinee);
        containerView.put(Entity.Container.SKILL_NAME, skillName);
        containerView.put(Entity.Container.CONTAINER_ID, container.getId());
        Storage.saveContainer(containerView);

        String containerId = container.getId();
        awaitContainerTermination(containerId);
        List<String> logs = getContainerLogs(containerId);

        boolean result = analyzeContainerLogs(logs);
        Map<String, Object> session = Storage.findSession(examinee)
                                             .get();
        Instant sessionStartTime = Collection.get(session, Entity.Session.STARTED);
        if (result) {
            Map<String, Object> examResult = new HashMap<>();
            examResult.put(Entity.Result.SKILL_NAME, skillName);
            examResult.put(Entity.Result.EXAMINEE, examinee);
            examResult.put(Entity.Result.DURATION_MINUTES, Duration.between(sessionStartTime, Instant.now()));
            Storage.saveResult(examResult);
        }
    }

    public static void awaitContainerTermination(String containerId) {
        val exception = new StillRunning();
        var info = DOCKER_CLIENT.inspectContainerCmd(containerId)
                                .exec();
        Failsafe.with(RETRY_POLICY)
                .run(() -> {
                    if (!("exited".equals(info.getState()
                                              .getStatus()))) {
                        throw exception;
                    }
                });
    }

    @SneakyThrows
    public static List<String> getContainerLogs(String containerId) {
        LogContainerCmd logCmd = DOCKER_CLIENT.logContainerCmd(containerId)
                                              .withStdOut(true)
                                              .withStdErr(true);

        List<String> logs = new ArrayList<>();
        logCmd.exec(new ResultCallback.Adapter<>() {
                  @Override
                  public void onNext(Frame object) {
                      logs.add(object.toString());
                  }
              })
              .awaitCompletion();
        return logs;
    }

    public static boolean analyzeContainerLogs(List<String> logs) {
        /*
        Главная задача Экзамена не логи анализировать, а проверить резульат. Пусть эта процедура выполняется в рамках проверки результата. Как именно — не важно.
         */
        var secret = Config.get("check-exam.secret");
        var successPattern = "%s".formatted(secret);
        return logs.stream()
                   .anyMatch(log -> log.contains(successPattern));
    }
}
