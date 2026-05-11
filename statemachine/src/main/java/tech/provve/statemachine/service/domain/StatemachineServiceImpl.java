package tech.provve.statemachine.service.domain;

import io.avaje.inject.External;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.libs.s3.S3Service;
import tech.provve.statemachine.CheckSolutionMachine;
import tech.provve.statemachine.SaveExamMachine;
import tech.provve.statemachine.domain.entity.CheckSolution;
import tech.provve.statemachine.domain.entity.SaveExam;
import tech.provve.statemachine.domain.value.CheckSolutionState;
import tech.provve.statemachine.domain.value.SaveExamState;
import tech.provve.statemachine.exception.StatemachineAlreadyExists;
import tech.provve.statemachine.repository.CheckSolutionRepository;
import tech.provve.statemachine.repository.SaveExamRepository;
import tech.provve.statemachine.specification.PrivateArchiveSpecification;
import tech.provve.validation.domain.entity.SolutionContainer;

import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static tech.provve.statemachine.SaveExamMachine.*;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class StatemachineServiceImpl implements StatemachineService {

    private final SaveExamRepository saveExamRepository;
    private final CheckSolutionRepository checkSolutionRepository;
    private final SolutionContainer solutionContainer;
    private final PrivateArchiveSpecification privateArchiveSpecification;

    @External
    @Named(DELAYED_EXAM_VOTE_CREATOR)
    private final Consumer<String> delayedExamVoteCreator;

    @External
    @Named(VALIDATION_ERROR_NOTIFICATION_SENDER)
    private final BiConsumer<String, String> validationErrorNotificationSender;

    @External
    @Named(EXAM_SAVED_NOTIFICATION_SENDER)
    private final BiConsumer<String, String> examSavedNotificationSender;

    @External
    private final S3Service s3Service;

    @Override
    public void continueAll() {
        saveExamRepository.list()
                          .stream()
                          .filter(s -> !SaveExamState.PREPARED.equals(s.state()))
                          .forEach(this::createSaveExam);

        checkSolutionRepository.list()
                               .stream()
                               .filter(s -> !CheckSolutionState.STOPPED.equals(s.state()))
                               .forEach(s -> createCheckSolution(s, Path.of("")));
    }

    @Override
    public void createSaveExam(String name, String author, String delayedVoteJson) throws StatemachineAlreadyExists {
        if (saveExamRepository.exists(name)) {
            throw new StatemachineAlreadyExists(name);
        }
        createSaveExam(new SaveExam(name, SaveExamState.UNPREPARED, author, delayedVoteJson));
    }

    private void createSaveExam(SaveExam saveExam) {
        var s = saveExamMachine();
        s.setInitialState(saveExam.state());
        s.init(saveExam.name(), saveExam.author(), saveExam.delayedVoteJson());
    }

    @Override
    public void createCheckSolution(String name, String examinee, Path solutionArchivePath) throws StatemachineAlreadyExists {
        if (checkSolutionRepository.exists(name)) {
            throw new StatemachineAlreadyExists(name);
        }
        createCheckSolution(new CheckSolution(name, CheckSolutionState.UNPREPARED, examinee), solutionArchivePath);
    }

    private void createCheckSolution(CheckSolution checkSolution, Path solutionArchivePath) {
        var s = checkSolutionMachine();
        s.setInitialState(checkSolution.state());
        s.init(checkSolution.name(), checkSolution.examinee(), solutionArchivePath);
    }

    private SaveExamMachine saveExamMachine() {
        return new SaveExamMachine(
                delayedExamVoteCreator, validationErrorNotificationSender, examSavedNotificationSender, privateArchiveSpecification, saveExamRepository,
                s3Service
        );
    }

    private CheckSolutionMachine checkSolutionMachine() {
        return new CheckSolutionMachine(solutionContainer, checkSolutionRepository, s3Service);
    }
}
