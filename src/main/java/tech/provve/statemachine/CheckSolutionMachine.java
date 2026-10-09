package tech.provve.statemachine;

import de.amr.statemachine.Match;
import de.amr.statemachine.StateMachine;
import io.avaje.config.Config;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import tech.provve.constants.Entity;
import tech.provve.statemachine.domain.value.CheckSolutionEvent;
import tech.provve.statemachine.domain.value.CheckSolutionState;
import tech.provve.util.Container;
import tech.provve.util.S3;
import tech.provve.util.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tech.provve.constants.Entity.PrivateArchive.IGNORE_FILE;
import static tech.provve.statemachine.domain.value.CheckSolutionEvent.*;
import static tech.provve.statemachine.domain.value.CheckSolutionState.*;

/**
 * МС для проверки решения от экзаменуемого
 */
public class CheckSolutionMachine extends StateMachine<CheckSolutionState, CheckSolutionEvent> {

    public CheckSolutionMachine() {
        super(CheckSolutionState.class, Match.BY_EQUALITY);
    }

    /**
     * @param name название навыка
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
                                S3.crtUpload(Config.get("s3.buckets.solutions"),
                                                                         S3.solutionArchiveKeygen(name, examinee), solutionArchive);
                                // извлек
                                Path solutionTempDirPath = Files.createTempDirectory(null);
                                new ZipFile(solutionArchivePath.toFile()).extractAll(solutionTempDirPath.toString());

                                // скачал экзамен и записал на диск
                                byte[] examArchive = S3.download(Config.get("s3.buckets.archives"), S3.privateArchiveKeygen(name));
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

                                S3.crtUpload(Config.get("s3.buckets.archives"), S3.solutionExamArchiveKeygen(name, examinee), Files.readAllBytes(examTempFile));

                                Map<String, Object> checkSolution = new HashMap<>();
                                checkSolution.put(Entity.CheckSolution.NAME, name);
                                checkSolution.put(Entity.CheckSolution.STATE, getState());
                                checkSolution.put(Entity.CheckSolution.EXAMINEE, examinee);
                                Storage.saveCheckSolution(checkSolution);

                                process(PREPARE);
                            } catch (IOException _) {
                                process(CRASH);
                            }
                        })
                        .onExit(() -> S3.delete(Config.get("s3.buckets.solutions"), S3.solutionArchiveKeygen(name, examinee)))
                    .state(PREPARED)
                        .onEntry(() -> {
                            try {
                                byte[] mergedSolutionExamArchive = S3.download(Config.get("s3.buckets.archives"), S3.solutionExamArchiveKeygen(name, examinee));
                                // извлек
                                Path tempArchivePath = Files.createTempFile(null, ".zip");
                                Files.write(tempArchivePath, mergedSolutionExamArchive);

                                Path tempDirPath = Files.createTempDirectory(null);
                                new ZipFile(tempArchivePath.toFile()).extractAll(tempDirPath.toString());

                                var secretPath = tempDirPath.resolve(Entity.PrivateArchive.INJECT_SECRET_FILE);
                                var secret = Files.readString(secretPath);
                                Config.setProperty("check-exam.secret", secret);
                                Files.delete(secretPath);

                                Container.buildDockerImage(examinee, tempDirPath);

                                Storage.updateCheckSolutionState(name, getState());

                                process(RUN);
                            } catch (IOException e) {
                                process(CRASH);
                            }
                        })
                    .state(RUNNING)
                        .onEntry(() -> {
                            Container.processContainer(examinee, name);
                            Storage.updateCheckSolutionState(name, getState());
                            process(STOP);
                        })
                    .state(STOPPED)
                        .onEntry(() -> {
                            S3.delete(Config.get("s3.buckets.archives"), S3.solutionExamArchiveKeygen(name, examinee));
                            Storage.updateCheckSolutionState(name, getState());
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
