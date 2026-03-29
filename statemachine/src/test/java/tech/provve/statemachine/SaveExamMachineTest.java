package tech.provve.statemachine;

import io.avaje.config.Config;
import io.avaje.inject.test.InjectTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import tech.provve.libs.s3.S3Service;
import tech.provve.statemachine.repository.SaveExamRepository;
import tech.provve.statemachine.service.ZipManipulator;
import tech.provve.statemachine.specification.PrivateArchiveSpecification;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.provve.statemachine.domain.value.PrivateArchive.INJECT_SECRET_PLACEHOLDER;
import static tech.provve.statemachine.service.ZipManipulator.extractFromZip;


@InjectTest
class SaveExamMachineTest {

    @Mock
    S3Client s3Client;

    @Mock
    S3AsyncClient s3AsyncClient;

    @Mock
    SaveExamRepository repository;

    @Test
    void init_givenArchiveWithoutSecretInjected_secretInjected() throws IOException {
        // arrange
        Config.setProperty("s3.buckets.exams", "");

        byte[] privateArchive = Files.readAllBytes(Path.of("src/test/resources/private-valid.zip"));
        final var updatedPrivateArchive = new ArrayList<byte[]>();
        var machine = getMachine(privateArchive, updatedPrivateArchive);

        // act
        machine.init("", "", "");

        // assert
        byte[] secret = extractFromZip("secret", new ZipInputStream(new ByteArrayInputStream(updatedPrivateArchive.getFirst())));
        assertThat(secret).hasSizeGreaterThan(0);

        String dockerfile = new String(extractFromZip("Dockerfile", new ZipInputStream(new ByteArrayInputStream(updatedPrivateArchive.getFirst()))));
        assertThat(dockerfile).isNotBlank()
                              .doesNotContain(INJECT_SECRET_PLACEHOLDER)
                              .contains(System.lineSeparator());
    }

    private SaveExamMachine getMachine(byte[] privateArchive, ArrayList<byte[]> updatedPrivateArchive) {
        var s3Service = new S3Service(s3Client, s3AsyncClient) {

            @Override
            public byte[] download(String bucket, String key) {
                return privateArchive;
            }

            @Override
            public String crtUpload(String bucket, String key, byte[] body) {
                updatedPrivateArchive.add(body);
                return "";
            }
        };
        var machine = new SaveExamMachine(
                s -> {
                }, (s, ss) -> {
        }, (s, ss) -> {
        },
                new PrivateArchiveSpecification(new ZipManipulator()),
                repository,
                s3Service
        );
        return machine;
    }

}