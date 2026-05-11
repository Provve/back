package tech.provve.validation.domain.entity;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.model.Frame;
import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.avaje.config.Config;
import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;
import lombok.val;
import tech.provve.skill.domain.entity.Result;
import tech.provve.skill.repository.ResultRepository;
import tech.provve.skill.repository.SessionRepository;
import tech.provve.validation.domain.value.ContainerView;
import tech.provve.validation.exception.StillRunning;
import tech.provve.validation.repository.ContainerRepository;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Singleton
public class SolutionContainer {

    private final ContainerRepository containerRepository;
    private final ResultRepository resultRepository;
    private final SessionRepository sessionRepository;

    private final DockerClient dockerClient;

    @External
    private final RetryPolicy<Void> retryPolicy = RetryPolicy.<Void>builder()
                                                             .withDelay(Duration.ofSeconds(10))
                                                             .withMaxRetries(50)
                                                             .handle(StillRunning.class)
                                                             .build();

    public SolutionContainer(@External DockerClient client,
                             ContainerRepository repository,
                             @External ResultRepository resultRepository,
                             @External SessionRepository sessionRepository) {
        dockerClient = client;
        containerRepository = repository;
        this.resultRepository = resultRepository;
        this.sessionRepository = sessionRepository;

        // todo когда добавлю события:
        //  - упростить поведение
        //  - убрать прцоедурные вызовы методов
        //  - создать контейнер при создании объекта
    }

    @SneakyThrows
    public void buildDockerImage(String examinee, Path dockerFileHomeDir) { //
        dockerClient.buildImageCmd(dockerFileHomeDir.toFile())
                    .withNetworkMode("none")
                    .withTags(Set.of(examinee))
                    .exec(new ResultCallback.Adapter<>() {
                        // ignore
                    })
                    .awaitCompletion();
    }

    public void runSolution(String examinee, String examName) {
        CreateContainerResponse container = dockerClient.createContainerCmd(examinee)
                                                        .withNetworkDisabled(true)
                                                        .exec();
        dockerClient.startContainerCmd(container.getId())
                    .exec();
        containerRepository.save(new ContainerView(examinee, examName, container.getId()));

        String containerId = container.getId();
        awaitContainerTermination(containerId);
        List<String> logs = getContainerLogs(containerId);

        boolean result = analyzeContainerLogs(logs);
        var sessionStartTime = sessionRepository.find(examinee)
                                                .get()
                                                .started();
        if (result) {
            resultRepository.save(new Result(examName, examinee, Duration.between(sessionStartTime, Instant.now())));
        }
    }

    private void awaitContainerTermination(String containerId) {
        val exception = new StillRunning();
        var info = dockerClient.inspectContainerCmd(containerId)
                               .exec();
        Failsafe.with(retryPolicy)
                .run(() -> {
                    if (!("exited".equals(info.getState()
                                              .getStatus()))) {
                        throw exception;
                    }
                });
    }

    @SneakyThrows
    private List<String> getContainerLogs(String containerId) {
        LogContainerCmd logCmd = dockerClient.logContainerCmd(containerId)
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

    private boolean analyzeContainerLogs(List<String> logs) {
        /*
        Главная задача Экзамена не логи анализировать, а проверить резульат. Пусть эта процедура выполняется в рамках проверки результата. Как именно — не важно.
         */
        var secret = Config.get("check-exam.secret");
        var successPattern = "%s".formatted(secret);
        return logs.stream()
                   .anyMatch(log -> log.contains(successPattern));
    }
}
