package tech.provve.statemachine.specification;

import tech.provve.statemachine.ZipManipulator;

import static tech.provve.constants.Entity.PrivateArchive.DOCKER_FILE;
import static tech.provve.constants.Entity.PrivateArchive.IGNORE_FILE;

public class PrivateArchiveSpecification {

    private static final String[] REQUIRED_FILES = new String[]{
            IGNORE_FILE,
            DOCKER_FILE
    };

    public static boolean isValid(byte[] archiveInputStream) {
        return ZipManipulator.isContainsAll(archiveInputStream, REQUIRED_FILES);
    }

}
