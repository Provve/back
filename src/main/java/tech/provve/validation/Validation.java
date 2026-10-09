package tech.provve.validation;

import lombok.SneakyThrows;
import tech.provve.api.generated.dto.Observation;
import tech.provve.constants.Entity;
import tech.provve.statemachine.domain.Statemachine;
import tech.provve.util.Storage;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Validation {

    public static void observed(Observation observation) {
        Map<String, Object> observed = new HashMap<>();
        observed.put(Entity.Observation.CHEATED, observation.getCheated());
        observed.put(Entity.Observation.VIOLATIONS, observation.getViolations());
        Storage.saveObservation(observed);
    }

    @SneakyThrows
    public static boolean validate(String examinee, String skillName, Path solutionArchivePath) {
        Statemachine.createCheckSolution(skillName, examinee, renameExtensionToZip(solutionArchivePath.toString()).toPath());
        return !(Storage.voteExists(skillName, true));
    }

    // Vertx записывает временные файлы с расришением .tmp, а нужен .zip
    private static File renameExtensionToZip(String notZipPath) {
        var zipFile = new File(notZipPath.replace(".tmp",
                                                  ".zip"));
        boolean ignored = new File(notZipPath).renameTo(zipFile);

        return zipFile;
    }
}
