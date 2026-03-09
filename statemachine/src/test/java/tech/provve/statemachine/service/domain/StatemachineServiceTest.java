package tech.provve.statemachine.service.domain;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import tech.provve.statemachine.exception.StatemachineAlreadyExists;
import tech.provve.statemachine.repository.CheckSolutionRepository;
import tech.provve.statemachine.repository.SaveExamRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@InjectTest
class StatemachineServiceTest {

    @Mock
    SaveExamRepository saveExamRepository;

    @Mock
    CheckSolutionRepository checkSolutionRepository;

    @Inject
    StatemachineService statemachineService;

    @Test
    void createSaveExam_alreadyExists_exception() {
        // arrange
        when(saveExamRepository.exists(any())).thenReturn(true);

        // act assert
        assertThrows(StatemachineAlreadyExists.class, () -> statemachineService.createSaveExam("", "", ""));
    }

    @Test
    void createCheckSolution_alreadyExists_exception() {
        // arrange
        when(checkSolutionRepository.exists(any())).thenReturn(true);

        // act assert
        assertThrows(StatemachineAlreadyExists.class, () -> statemachineService.createCheckSolution("", ""));
    }

}