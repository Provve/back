package tech.provve.statemachine.domain;


import alekseyvideman.dop.Collection;
import tech.provve.accounts.Account;
import tech.provve.constants.Entity;
import tech.provve.task.Scheduling;
import tech.provve.notification.NotificationSending;

import tech.provve.statemachine.CheckSolutionMachine;
import tech.provve.statemachine.SaveExamMachine;
import tech.provve.statemachine.domain.value.CheckSolutionState;
import tech.provve.statemachine.domain.value.SaveExamState;
import tech.provve.statemachine.exception.StatemachineAlreadyExists;
import tech.provve.util.Jackson;
import tech.provve.util.Storage;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DatabindException;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Statemachine {
    public static Consumer<String> delayedExamVoteCreator = delayedVoteJson -> {
        try {
            var deadline = LocalDateTime.now(ZoneOffset.UTC)
                                        .plusMonths(1);
            Map<String, Object> vote = Jackson.json.readValue(delayedVoteJson, new TypeReference<Map<String, Object>>() {
            });
            vote.put(Entity.Vote.DEADLINE, deadline);
            Storage.saveVote(vote);

            String voteName = Collection.get(vote, Entity.Vote.NAME);
            Map<String, Object> exam = Collection.get(vote, Entity.Vote.EXAM);
            String skillName = Collection.get(exam, Entity.Exam.SKILL_NAME);

            Scheduling.addExam(voteName,
                               skillName,
                               deadline.toInstant(ZoneOffset.UTC));
            Account.notifyVoteStarted(voteName, skillName);
        } catch (DatabindException e) {
            throw new RuntimeException("Couldn't create Vote from given json:" + e);
        }
    };

    public static BiConsumer<String, String> validationErrorNotificationSender = (exam, author) -> {
        String email = Storage.findAccountByLogin(author)
                              .map(account -> Collection.<String>getOrNull(account, Entity.Account.EMAIL))
                              .orElse(null);
        NotificationSending.send(
                NotificationSending.authoredExamNotSavedCommand(author, email),
                NotificationSending.fillAuthoredExamNotSavedTemplate(author, exam)
        );
    };

    public static BiConsumer<String, String> examSavedNotificationSender = (exam, author) -> {
        String email = Storage.findAccountByLogin(author)
                              .map(account -> Collection.<String>getOrNull(account, Entity.Account.EMAIL))
                              .orElse(null);
        NotificationSending.send(
                NotificationSending.authoredExamSavedCommand(author, email),
                NotificationSending.fillAuthoredExamSavedTemplate(author, exam)
        );
    };

    public static void continueAll() {
        Storage.listSaveExams()
               .stream()
               .filter(s -> !SaveExamState.PREPARED.equals(Collection.get(s, Entity.SaveExam.STATE)))
               .forEach(saveExam -> {

                   var s = new SaveExamMachine();
                   s.setInitialState(Collection.get(saveExam, Entity.SaveExam.STATE));
                   s.init(Collection.get(saveExam, Entity.SaveExam.NAME),
                          Collection.get(saveExam, Entity.SaveExam.AUTHOR),
                          Collection.get(saveExam, Entity.SaveExam.DELAYED_VOTE_JSON));
               });

        Storage.listCheckSolutions()
               .stream()
               .filter(s -> !CheckSolutionState.STOPPED.equals(Collection.get(s, Entity.CheckSolution.STATE)))
               .forEach(checkSolution -> {
                   var s = new CheckSolutionMachine();
                   s.setInitialState(Collection.get(checkSolution, Entity.CheckSolution.STATE));
                   s.init(Collection.get(checkSolution, Entity.CheckSolution.NAME),
                          Collection.get(checkSolution, Entity.CheckSolution.EXAMINEE),
                          Path.of(""));
               });
    }

    public static void createSaveExam(String name, String author, String delayedVoteJson) throws StatemachineAlreadyExists {
        if (Storage.saveExamExists(name)) {
            throw new StatemachineAlreadyExists(name);
        }
        Map<String, Object> saveExam = new HashMap<>();
        saveExam.put(Entity.SaveExam.NAME, name);
        saveExam.put(Entity.SaveExam.STATE, SaveExamState.UNPREPARED);
        saveExam.put(Entity.SaveExam.AUTHOR, author);
        saveExam.put(Entity.SaveExam.DELAYED_VOTE_JSON, delayedVoteJson);
        createSaveExam(saveExam);
    }

    private static void createSaveExam(Map<String, Object> saveExam) {
        var s = saveExamMachine();
        s.setInitialState(Collection.get(saveExam, Entity.SaveExam.STATE));
        s.init(Collection.get(saveExam, Entity.SaveExam.NAME),
               Collection.get(saveExam, Entity.SaveExam.AUTHOR),
               Collection.get(saveExam, Entity.SaveExam.DELAYED_VOTE_JSON));
    }

    public static void createCheckSolution(String name, String examinee, Path solutionArchivePath) throws StatemachineAlreadyExists {
        if (Storage.checkSolutionExists(name)) {
            throw new StatemachineAlreadyExists(name);
        }
        Map<String, Object> checkSolution = new HashMap<>();
        checkSolution.put(Entity.CheckSolution.NAME, name);
        checkSolution.put(Entity.CheckSolution.STATE, CheckSolutionState.UNPREPARED);
        checkSolution.put(Entity.CheckSolution.EXAMINEE, examinee);
        createCheckSolution(checkSolution, solutionArchivePath);
    }

    private static void createCheckSolution(Map<String, Object> checkSolution, Path solutionArchivePath) {
        var s = checkSolutionMachine();
        s.setInitialState(Collection.get(checkSolution, Entity.CheckSolution.STATE));
        s.init(Collection.get(checkSolution, Entity.CheckSolution.NAME),
               Collection.get(checkSolution, Entity.CheckSolution.EXAMINEE),
               solutionArchivePath);
    }

    private static SaveExamMachine saveExamMachine() {
        return new SaveExamMachine();
    }

    private static CheckSolutionMachine checkSolutionMachine() {
        return new CheckSolutionMachine();
    }

}
