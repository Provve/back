package tech.provve.statemachine.service;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class ZipManipulatorTest {

    ZipManipulator zipManipulator = new tech.provve.statemachine.service.ZipManipulator();

    @Test
    void isContainsAll_allEntriesPresent_true() throws IOException {
        // arrange
        var required = new String[]{
                "ignore.txt",
                "Dockerfile"
        };
        var zipStream = createZip(required);

        // act
        boolean valid = zipManipulator.isContainsAll(zipStream, required);

        // assert
        assertThat(valid).isTrue();
    }

    @Test
    void isContainsAll_someEntriesMissing_false() throws IOException {
        // arrange
        var required = new String[]{
                "ignore.txt",
                "Dockerfile"
        };
        var zip = createZip("ignore.txt");

        // act
        boolean valid = zipManipulator.isContainsAll(zip, required);

        // assert
        assertThat(valid).isFalse();
    }

    private byte[] createZip(String... entries) throws IOException {
        ByteArrayOutputStream zipStream = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(zipStream);

        for (String entry : entries) {
            zos.putNextEntry(new ZipEntry(entry));
            zos.closeEntry();
        }

        return zipStream.toByteArray();
    }

}