package tech.provve.statemachine;

import de.amr.statemachine.Match;
import de.amr.statemachine.StateMachine;
import io.avaje.config.Config;
import lombok.SneakyThrows;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import tech.provve.constants.Entity;
import tech.provve.statemachine.domain.Statemachine;
import tech.provve.statemachine.domain.value.SaveSkillEvent;
import tech.provve.statemachine.domain.value.SaveSkillState;
import tech.provve.statemachine.specification.PrivateArchiveSpecification;
import tech.provve.statemachine.specification.PublicArchiveSpecification;
import tech.provve.util.S3;
import tech.provve.util.Storage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static tech.provve.constants.Entity.PrivateArchive.*;
import static tech.provve.statemachine.SecretGenerator.bytesToHex;
import static tech.provve.statemachine.SecretGenerator.generateSecret;
import static tech.provve.statemachine.ZipManipulator.extractFromZip;
import static tech.provve.statemachine.domain.value.SaveSkillEvent.INVALIDATE;
import static tech.provve.statemachine.domain.value.SaveSkillEvent.PREPARE;
import static tech.provve.statemachine.domain.value.SaveSkillState.*;

/**
 * МС для сохранения навыка вместе с приватным и публичным архивами экзамена
 */
public class SaveSkillMachine extends StateMachine<SaveSkillState, SaveSkillEvent> {

    public SaveSkillMachine() {
        super(SaveSkillState.class, Match.BY_EQUALITY);
    }

    public void init(String name, String author, String delayedVoteJson) {
        //@formatter:off
        beginStateMachine()
                .description("Save Skill")
                .initialState(UNPREPARED)
                .states()
                    .state(UNPREPARED)
                        .onEntry(() -> {
                            var bucket = Config.get("s3.buckets.archives");
                            var privateArchiveKey = S3.privateArchiveKeygen(name);
                            var publicArchiveKey = S3.publicArchiveKeygen(name);

                            byte[] privateArchiveData = S3.download(bucket, privateArchiveKey);
                            byte[] publicArchiveData = S3.download(bucket, publicArchiveKey);

                            boolean valid = PrivateArchiveSpecification.isValid(privateArchiveData)
                                    && PublicArchiveSpecification.isValid(publicArchiveData);
                            if (!valid) {
                                process(INVALIDATE);
                                return;
                            }

                            byte[] updatedArchive = injectSecret(privateArchiveData);
                            S3.crtUpload(bucket, privateArchiveKey, updatedArchive);

                            Map<String, Object> saveSkill = new HashMap<>();
                            saveSkill.put(Entity.SaveSkill.NAME, name);
                            saveSkill.put(Entity.SaveSkill.STATE, getState());
                            saveSkill.put(Entity.SaveSkill.AUTHOR, author);
                            saveSkill.put(Entity.SaveSkill.DELAYED_VOTE_JSON, delayedVoteJson);
                            Storage.saveSaveSkill(saveSkill);

                            process(PREPARE);
                        })
                    .state(PREPARED)
                        .onEntry(() -> {
                            Statemachine.delayedSkillVoteCreator.accept(delayedVoteJson);
                            Statemachine.skillSavedNotificationSender.accept(name, author);
                            Storage.updateSaveSkillState(name, getState());
                        })
                    .state(INVALID)
                        .onEntry(() -> {
                            var bucket = Config.get("s3.buckets.archives");
                            S3.delete(bucket, S3.privateArchiveKeygen(name));
                            S3.delete(bucket, S3.publicArchiveKeygen(name));

                            Statemachine.validationErrorNotificationSender.accept(name, author);
                            Storage.deleteSaveSkill(name);
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
