package tech.provve.statemachine;

import de.amr.statemachine.Match;
import de.amr.statemachine.StateMachine;
import io.avaje.config.Config;
import jakarta.inject.Named;
import lombok.SneakyThrows;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import tech.provve.statemachine.domain.entity.SaveExam;
import tech.provve.statemachine.domain.value.SaveExamEvent;
import tech.provve.statemachine.domain.value.SaveExamState;
import tech.provve.statemachine.repository.SaveExamRepository;
import tech.provve.statemachine.specification.PrivateArchiveSpecification;
import terch.provve.libs.s3.S3Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static java.nio.charset.StandardCharsets.UTF_8;
import static tech.provve.statemachine.domain.value.PrivateArchive.INJECT_SECRET_PLACEHOLDER;
import static tech.provve.statemachine.domain.value.SaveExamEvent.INVALIDATE;
import static tech.provve.statemachine.domain.value.SaveExamEvent.PREPARE;
import static tech.provve.statemachine.domain.value.SaveExamState.*;
import static tech.provve.statemachine.service.SecretGenerator.bytesToHex;
import static tech.provve.statemachine.service.SecretGenerator.generateSecret;

/**
 * МС для сохранения приватного архива экзамена
 */
public class SaveExamMachine extends StateMachine<SaveExamState, SaveExamEvent> {

    public static final String VALIDATION_ERROR_NOTIFICATION_SENDER = "1";
    public static final String EXAM_SAVED_NOTIFICATION_SENDER = "2";
    public static final String DELAYED_EXAM_VOTE_CREATOR = "3";

    private final Consumer<String> delayedExamVoteCreator;

    @Named(VALIDATION_ERROR_NOTIFICATION_SENDER)
    private final BiConsumer<String, String> validationErrorNotificationSender;

    @Named(EXAM_SAVED_NOTIFICATION_SENDER)
    private final BiConsumer<String, String> examSavedNotificationSender;
    private final PrivateArchiveSpecification privateArchiveSpecification;
    private final SaveExamRepository repository;
    private final S3Service s3Service;

    public SaveExamMachine(Consumer<String> delayedExamVoteCreator, BiConsumer<String, String> validationErrorNotificationSender1, BiConsumer<String, String> examSavedNotificationSender1, PrivateArchiveSpecification privateArchiveSpecification, SaveExamRepository repository, S3Service s3Service) {
        super(SaveExamState.class, Match.BY_EQUALITY);
        this.delayedExamVoteCreator = delayedExamVoteCreator;
        this.validationErrorNotificationSender = validationErrorNotificationSender1;
        this.examSavedNotificationSender = examSavedNotificationSender1;
        this.privateArchiveSpecification = privateArchiveSpecification;
        this.repository = repository;
        this.s3Service = s3Service;
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
                            var privateArchiveKey = S3Service.privateArchiveKeygen(name);
                            byte[] privateArchiveData = s3Service.download(bucket, privateArchiveKey);

                            boolean valid = privateArchiveSpecification.isValid(privateArchiveData);
                            if (!valid) process(INVALIDATE);

                            byte[] updatedArchive = injectSecret(privateArchiveData);
                            s3Service.crtUpload(bucket, privateArchiveKey, updatedArchive);

                            repository.save(new SaveExam(name, getState(), author, delayedVoteJson));

                            process(PREPARE);
                        })
                    .state(PREPARED)
                        .onEntry(() -> {
                            delayedExamVoteCreator.accept(delayedVoteJson);

                        /*
                        Сохранил json с голосованием в таблицу, извлекаю её и создаю голосование через delayedExamVoteCreator.
                        Проблемы нет.
                         */

                            examSavedNotificationSender.accept(name, author);
                            repository.updateState(name, getState());
                        })
                    .state(INVALID)
                        .onEntry(() -> {
                            var bucket = Config.get("s3.buckets.exams");
                            s3Service.delete(bucket, S3Service.privateArchiveKeygen(name));
                            s3Service.delete(bucket, S3Service.publicArchiveKeygen(name));

                            validationErrorNotificationSender.accept(name, author);
                            repository.delete(name);
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
            replacePlaceholderInDockerfile(zipFile, secret);

            ZipParameters params = new ZipParameters();
            params.setFileNameInZip("secret");
            zipFile.addStream(new ByteArrayInputStream(secret.getBytes(UTF_8)), params);

            return Files.readAllBytes(tempArchive);
        } finally {
            Files.deleteIfExists(tempArchive);
        }
    }

    private void replacePlaceholderInDockerfile(ZipFile zipFile, String replacement) throws IOException {
        var dockerFile = "Dockerfile";
        var dockerFileHeader = new FileHeader();
        dockerFileHeader.setFileName(dockerFile);

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(zipFile.getInputStream(dockerFileHeader))
                );
                ByteArrayOutputStream writer = new ByteArrayOutputStream()
        ) {
            reader.lines()
                  .map(line -> line.replace(INJECT_SECRET_PLACEHOLDER, replacement))
                  .map(String::getBytes)
                  .forEach(writer::writeBytes);

            var params = new ZipParameters();
            params.setFileNameInZip(dockerFile);
            zipFile.addStream(new ByteArrayInputStream(writer.toByteArray()), params);
        }
    }
}
