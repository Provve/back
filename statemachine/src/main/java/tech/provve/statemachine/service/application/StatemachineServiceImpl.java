package tech.provve.statemachine.service.application;

import io.avaje.inject.BeanScope;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.statemachine.CheckSolutionMachine;
import tech.provve.statemachine.SaveExamMachine;
import tech.provve.statemachine.domain.entity.CheckSolution;
import tech.provve.statemachine.domain.entity.SaveExam;
import tech.provve.statemachine.domain.value.CheckSolutionState;
import tech.provve.statemachine.domain.value.SaveExamState;
import tech.provve.statemachine.exception.StatemachineAlreadyExists;
import tech.provve.statemachine.repository.CheckSolutionRepository;
import tech.provve.statemachine.repository.SaveExamRepository;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class StatemachineServiceImpl implements StatemachineService {

    private final BeanScope beanScope;
    private final SaveExamRepository saveExamRepository;
    private final CheckSolutionRepository checkSolutionRepository;

    @Override
    public void continueAll() {
        saveExamRepository.list()
                          .stream()
                          .filter(s -> !SaveExamState.PREPARED.equals(s.state()))
                          .forEach(this::createSaveExam);

        checkSolutionRepository.list()
                               .stream()
                               .filter(s -> !CheckSolutionState.STOPPED.equals(s.state()))
                               .forEach(this::createCheckSolution);
    }

    @Override
    public void createSaveExam(String name, String author, String delayedVoteJson) throws StatemachineAlreadyExists {
        if (saveExamRepository.exists(name)) {
            throw new StatemachineAlreadyExists(name);
        }
        createSaveExam(new SaveExam(name, SaveExamState.UNPREPARED, author, delayedVoteJson));
    }

    private void createSaveExam(SaveExam saveExam) {
        var s = beanScope.get(SaveExamMachine.class);
        s.setInitialState(saveExam.state());
        s.init(saveExam.name(), saveExam.author(), saveExam.delayedVoteJson());
    }

    @Override
    public void createCheckSolution(String name, String examinee) throws StatemachineAlreadyExists {
        if (checkSolutionRepository.exists(name)) {
            throw new StatemachineAlreadyExists(name);
        }
        createCheckSolution(new CheckSolution(name, CheckSolutionState.UNPREPARED, examinee));
    }

    private void createCheckSolution(CheckSolution checkSolution) {
        var s = beanScope.get(CheckSolutionMachine.class);
        s.setInitialState(checkSolution.state());
        s.init(checkSolution.name(), checkSolution.examinee());
    }
}
