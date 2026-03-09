package tech.provve.statemachine.specification;

import jakarta.inject.Singleton;
import tech.provve.statemachine.service.ZipManipulator;

@Singleton
public class PrivateArchiveSpecification {

    private static final String[] REQUIRED_FILES = new String[]{
            "ignore.txt",
            "Dockerfile"
    };

    private final ZipManipulator zipManipulator;

    public PrivateArchiveSpecification(ZipManipulator reader) {
        zipManipulator = reader;
    }

    public boolean isValid(byte[] archiveInputStream) {
        return zipManipulator.isContainsAll(archiveInputStream, REQUIRED_FILES);
    }

}
