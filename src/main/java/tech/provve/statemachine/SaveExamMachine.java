package tech.provve.statemachine;

import de.amr.statemachine.Match;
import de.amr.statemachine.StateMachine;
import io.avaje.config.Config;
import lombok.SneakyThrows;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import tech.provve.constants.Entity;
import tech.provve.util.S3;
import tech.provve.statemachine.domain.value.SaveExamEvent;
import tech.provve.statemachine.domain.value.SaveExamState;
import tech.provve.statemachine.domain.Statemachine;
import tech.provve.statemachine.specification.PrivateArchiveSpecification;
import tech.provve.util.Storage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static tech.provve.constants.Entity.PrivateArchive.*;
import static tech.provve.statemachine.domain.value.SaveExamEvent.INVALIDATE;
import static tech.provve.statemachine.domain.value.SaveExamEvent.PREPARE;
import static tech.provve.statemachine.domain.value.SaveExamState.*;
import static tech.provve.statemachine.SecretGenerator.bytesToHex;
import static tech.provve.statemachine.SecretGenerator.generateSecret;
import static tech.provve.statemachine.ZipManipulator.extractFromZip;

/**
 * МС для сохранения приватного архива экзамена
 */
public class SaveExamMachine extends StateMachine<SaveExamState, SaveExamEvent> {

    public SaveExamMachine() {
        super(SaveExamState.class, Match.BY_EQUALITY);
    }

    public void init(String name, String author, String delayedVoteJson) {
        //@formatter:off
        beginStateMachine()
                .description("Save Exam")
                .initialState(UNPREPARED)
                .states()
                    .state(UNPREPARED)
                        .onEntry(() -> {
                            var bucket = Config.get("s3.buckets.exams");
                            var privateArchiveKey = S3.privateArchiveKeygen(name);
                            byte[] privateArchiveData = S3.download(bucket, privateArchiveKey);

                            boolean valid = PrivateArchiveSpecification.isValid(privateArchiveData);
                            if (!valid) process(INVALIDATE);

                            byte[] updatedArchive = injectSecret(privateArchiveData);
                            S3.crtUpload(bucket, privateArchiveKey, updatedArchive);

                            Map<String, Object> saveExam = new HashMap<>();
                            saveExam.put(Entity.SaveExam.NAME, name);
                            saveExam.put(Entity.SaveExam.STATE, getState());
                            saveExam.put(Entity.SaveExam.AUTHOR, author);
                            saveExam.put(Entity.SaveExam.DELAYED_VOTE_JSON, delayedVoteJson);
                            Storage.saveSaveExam(saveExam);

                            process(PREPARE);
                        })
                    .state(PREPARED)
                        .onEntry(() -> {
                            Statemachine.delayedExamVoteCreator.accept(delayedVoteJson);
                            Statemachine.examSavedNotificationSender.accept(name, author);
                            Storage.updateSaveExamState(name, getState());
                        })
                    .state(INVALID)
                        .onEntry(() -> {
                            var bucket = Config.get("s3.buckets.exams");
                            S3.delete(bucket, S3.privateArchiveKeygen(name));
                            S3.delete(bucket, S3.publicArchiveKeygen(name));

                            Statemachine.validationErrorNotificationSender.accept(name, author);
                            Storage.deleteSaveExam(name);
                        })
                .transitions()
                    .when(UNPREPARED).then(PREPARED).on(PREPARE)
                    .when(UNPREPARED).then(INVALID).on(INVALIDATE)
        .endStateMachine();
        //@formatter:on

        super.init();
    }

    /**
     * @param privateArchiveData data with secret placeholder
     * @return data with injected secret
     */
    @SneakyThrows
    private byte[] injectSecret(byte[] privateArchiveData) {
        Path tempArchive = Files.createTempFile(null, ".zip");
        Files.write(tempArchive, privateArchiveData);

        try (ZipFile zipFile = new ZipFile(tempArchive.toString())) {
            String secret = bytesToHex(generateSecret(16));
            replacePlaceholderInDockerfile(zipFile, privateArchiveData, secret);

            ZipParameters params = new ZipParameters();
            params.setFileNameInZip(INJECT_SECRET_FILE);
            zipFile.addStream(new ByteArrayInputStream(secret.getBytes(UTF_8)), params);

            return Files.readAllBytes(tempArchive);
        } finally {
            Files.deleteIfExists(tempArchive);
        }
    }

    private void replacePlaceholderInDockerfile(ZipFile zipFile, byte[] data, String replacement) throws IOException {
        var dockerFileHeader = new FileHeader();
        dockerFileHeader.setFileName(DOCKER_FILE);

        try (
                var zipInputStream = new ZipInputStream(new ByteArrayInputStream(data));
                var reader = new BufferedReader(
                        new InputStreamReader(new ByteArrayInputStream(extractFromZip(DOCKER_FILE, zipInputStream)), UTF_8));
                var out = new ByteArrayOutputStream();
        ) {
            reader.lines()
                  .map(line -> line.replace(INJECT_SECRET_PLACEHOLDER, replacement))
                  .map(line -> line + System.lineSeparator())
                  .map(String::getBytes)
                  .forEachOrdered(out::writeBytes);

            var params = new ZipParameters();
            params.setFileNameInZip(DOCKER_FILE);
            zipFile.addStream(new ByteArrayInputStream(out.toByteArray()), params);
        }
    }
}
