package tech.provve.statemachine.specification;

import jakarta.inject.Singleton;
import tech.provve.statemachine.service.ZipManipulator;

import static tech.provve.statemachine.domain.value.PrivateArchive.DOCKER_FILE;
import static tech.provve.statemachine.domain.value.PrivateArchive.IGNORE_FILE;

@Singleton
public class PrivateArchiveSpecification {

    private static final String[] REQUIRED_FILES = new String[]{
            IGNORE_FILE,
            DOCKER_FILE
    };

    private final ZipManipulator zipManipulator;

    public PrivateArchiveSpecification(ZipManipulator reader) {
        zipManipulator = reader;
    }

    public boolean isValid(byte[] archiveInputStream) {
        return zipManipulator.isContainsAll(archiveInputStream, REQUIRED_FILES);
    }

}
