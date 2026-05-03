package tech.provve.validation.service;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;
import tech.provve.api.server.generated.dto.Observation;
import tech.provve.skill.repository.VoteRepository;
import tech.provve.statemachine.service.domain.StatemachineService;
import tech.provve.validation.mapper.ObservationMapper;
import tech.provve.validation.repository.ObservationRepository;

import java.io.File;
import java.nio.file.Path;

@Singleton
public class ValidationServiceImpl implements ValidationService {

    private final ObservationRepository observationRepository;
    private final VoteRepository voteRepository;
    private final StatemachineService statemachineService;

    public ValidationServiceImpl(ObservationRepository repository, @External VoteRepository voteRepository, @External StatemachineService service) {
        observationRepository = repository;
        this.voteRepository = voteRepository;
        statemachineService = service;
    }

    @Override
    public void observed(Observation observation) {
        observationRepository.save(ObservationMapper.INST.map(observation));
    }

    @Override
    @SneakyThrows
    public boolean validate(String examinee, String examName, Path solutionArchivePath) {
        statemachineService.createCheckSolution(examName, examinee, renameExtensionToZip(solutionArchivePath.toString()).toPath());
        return !(voteRepository.exists(examName, true));
    }

    // Vertx записывает временные файлы с расришением .tmp, а нужен .zip
    private File renameExtensionToZip(String notZipPath) {
        var zipFile = new File(notZipPath.replace(".tmp",
                                                  ".zip"));
        new File(notZipPath).renameTo(zipFile);

        return zipFile;
    }
}
