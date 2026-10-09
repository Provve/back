package tech.provve.statemachine.specification;

import tech.provve.statemachine.ZipManipulator;

public class PublicArchiveSpecification {

    /**
     * Публичный архив (задание) должен быть валидным непустым zip-архивом.
     */
    public static boolean isValid(byte[] archiveData) {
        return ZipManipulator.isValid(archiveData);
    }

}
