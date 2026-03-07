package tech.provve.statemachine.service;

import jakarta.inject.Singleton;
import lombok.SneakyThrows;
import org.jspecify.annotations.NonNull;

import java.io.ByteArrayInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Singleton
public class ZipManipulator {

    /**
     * Check if the stream contains all specified files.
     */
    @SneakyThrows
    public boolean isContainsAll(byte[] zipData, @NonNull String[] filesInside) {
        try (var byteArray = new ByteArrayInputStream(zipData);
             var zis = new ZipInputStream(byteArray)) {
            int fitFiles = 0;

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                for (String fileInside : filesInside) {
                    if (fileInside.equals(entry.getName())) {
                        fitFiles++;
                    }
                }
            }

            return fitFiles == filesInside.length;
        }
    }

}
