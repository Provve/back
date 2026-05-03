package tech.provve.statemachine;

import de.amr.statemachine.Match;
import de.amr.statemachine.StateMachine;
import io.avaje.config.Config;
import io.avaje.inject.External;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import tech.provve.libs.s3.S3Service;
import tech.provve.statemachine.domain.entity.CheckSolution;
import tech.provve.statemachine.domain.value.CheckSolutionEvent;
import tech.provve.statemachine.domain.value.CheckSolutionState;
import tech.provve.statemachine.domain.value.PrivateArchive;
import tech.provve.statemachine.repository.CheckSolutionRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.BiConsumer;

import static tech.provve.statemachine.domain.value.CheckSolutionEvent.*;
import static tech.provve.statemachine.domain.value.CheckSolutionState.*;
import static tech.provve.statemachine.domain.value.PrivateArchive.IGNORE_FILE;

/**
 * МС для проверки решения от экзаменуемого
 */
@Singleton
public class CheckSolutionMachine extends StateMachine<CheckSolutionState, CheckSolutionEvent> {

    public static final String BUILD_IMAGE_FOR_EXAMINEE = "Check/1";
    public static final String PROCESS_CONTAINER = "Check/2";

    private final CheckSolutionRepository repository;
    private final S3Service s3;

    /**
     * Build image with exam + solution files and name it after an examinee
     */
    private final BiConsumer<String, Path> buildDockerImage;

    /**
     * Create container from image named after an examinee. Process its whole lifecycle.
     */
    private final BiConsumer<String, String> processContainer;

    public CheckSolutionMachine(CheckSolutionRepository repository,
                                S3Service s3,

                                @External
                                @Named(PROCESS_CONTAINER) BiConsumer<String, String> processContainer,

                                @External
                                @Named(BUILD_IMAGE_FOR_EXAMINEE) BiConsumer<String, Path> buildDockerImage) {
        super(CheckSolutionState.class, Match.BY_EQUALITY);
        this.repository = repository;
        this.s3 = s3;
        this.processContainer = processContainer;
        this.buildDockerImage = buildDockerImage;
    }

    /**
     * @param name {@link CheckSolution#name()}
     */
    public void init(String name, String examinee, Path solutionArchivePath) {
//        @formatter:off
        beginStateMachine()
                .description("Check Solution")
                .initialState(UNPREPARED)
                .states()
                    .state(UNPREPARED)
                        .onEntry(() -> {
                            try {
                                // сохранил решение в s3
                                byte[] solutionArchive = Files.readAllBytes(solutionArchivePath);
                                s3.crtUpload(Config.get("s3.buckets.solutions"),
                                                                         S3Service.solutionArchiveKeygen(name, examinee), solutionArchive);
                                // извлек
                                Path solutionTempDirPath = Files.createTempDirectory(null);
                                new ZipFile(solutionArchivePath.toFile()).extractAll(solutionTempDirPath.toString());

                                // скачал экзамен и записал на диск
                                byte[] examArchive = s3.download(Config.get("s3.buckets.exams"), S3Service.privateArchiveKeygen(name));
                                Path examTempFile = Files.createTempFile(null, ".zip");
                                Files.write(Path.of(examTempFile.toString()), examArchive);

                                // извлек ignore.txt
                                String ignoreListDirPath = Files.createTempDirectory(null).toString();
                                new ZipFile(solutionArchivePath.toFile()).extractFile(IGNORE_FILE, ignoreListDirPath);

                                // добавил в архив экзамена файлы решения, предварительно отфильтровав их
                                var ignoreListFilePath = Path.of(ignoreListDirPath, IGNORE_FILE);
                                List<String> ignoreList = Files.readAllLines(ignoreListFilePath);
                                ZipParameters zipParameters = new ZipParameters();
                                zipParameters.setExcludeFileFilter(ignoreList::contains);
                                new ZipFile(examTempFile.toFile()).addFolder(solutionTempDirPath.toFile(), zipParameters);

                                s3.crtUpload(Config.get("s3.buckets.exams"), S3Service.solutionExamArchiveKeygen(name, examinee), Files.readAllBytes(examTempFile));

                                repository.save(new CheckSolution(name, getState(), examinee));

                                process(PREPARE);
                            } catch (IOException _) {
                                process(CRASH);
                            }
                        })
                        .onExit(() -> s3.delete(Config.get("s3.buckets.solutions"), S3Service.solutionArchiveKeygen(name, examinee)))
                    .state(PREPARED)
                        .onEntry(() -> {
                            try {
                                byte[] mergedSolutionExamArchive = s3.download(Config.get("s3.buckets.exams"), S3Service.solutionExamArchiveKeygen(name, examinee));
                                // извлек
                                Path tempArchivePath = Files.createTempFile(null, ".zip");
                                Files.write(tempArchivePath, mergedSolutionExamArchive);

                                Path tempDirPath = Files.createTempDirectory(null);
                                new ZipFile(tempArchivePath.toFile()).extractAll(tempDirPath.toString());

                                var secretPath = tempDirPath.resolve(PrivateArchive.INJECT_SECRET_FILE);
                                var secret = Files.readString(secretPath);
                                Config.setProperty("check-exam.secret", secret);
                                Files.delete(secretPath);

                                buildDockerImage.accept(examinee, tempDirPath);

                                repository.updateState(name, getState());

                                process(RUN);
                            } catch (IOException e) {
                                process(CRASH);
                            }
                        })
                    .state(RUNNING)
                        .onEntry(() -> {
                            processContainer.accept(examinee, name);
                            repository.updateState(name, getState());
                            process(STOP);
                        })
                    .state(STOPPED)
                        .onEntry(() -> {
                            s3.delete(Config.get("s3.buckets.exams"), S3Service.solutionExamArchiveKeygen(name, examinee));
                            repository.updateState(name, getState());
                        })
                    .state(CRASHED)
                .transitions()
                    .when(UNPREPARED).then(PREPARED).on(PREPARE)
                    .when(PREPARED).then(RUNNING).on(RUN)
                    .when(RUNNING).then(STOPPED).on(STOP)
                    .when(RUNNING).then(CRASHED).on(CRASH)
        .endStateMachine();
        //@formatter:on

        super.init();
    }

}
