package tech.provve.statemachine;


import lombok.SneakyThrows;
import org.jspecify.annotations.NonNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ZipManipulator {

    @SneakyThrows
    public static byte[] extractFromZip(String fileName, ZipInputStream z) {
        ZipEntry ze;
        while ((ze = z.getNextEntry()) != null) {
            if (fileName.equals(ze.getName()))
                return z.readAllBytes();
        }

        return new byte[0];
    }

    /**
     * Check if the stream contains all specified files.
     */
    @SneakyThrows
    public static boolean isContainsAll(byte[] zipData, @NonNull String[] filesInside) {
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

    /**
     * Проверяет, что данные являются валидным непустым zip-архивом.
     */
    public static boolean isValid(byte[] zipData) {
        try (var byteArray = new ByteArrayInputStream(zipData);
             var zis = new ZipInputStream(byteArray)) {
            return zis.getNextEntry() != null;
        } catch (IOException e) {
            return false;
        }
    }

}
