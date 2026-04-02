package tech.provve.validation.service.domain;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.api.server.generated.dto.Observation;
import tech.provve.validation.mapper.ObservationMapper;
import tech.provve.validation.repository.ObservationRepository;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ValidationServiceImpl implements ValidationService {

    private final ObservationRepository observationRepository;

    @Override
    public void observed(Observation observation) {
        observationRepository.save(ObservationMapper.INST.map(observation));
    }
}
