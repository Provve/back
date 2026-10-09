package tech.provve.statemachine.domain;


import alekseyvideman.dop.Collection;
import tech.provve.accounts.Account;
import tech.provve.constants.Entity;
import tech.provve.notification.NotificationSending;
import tech.provve.statemachine.CheckSolutionMachine;
import tech.provve.statemachine.SaveSkillMachine;
import tech.provve.statemachine.domain.value.CheckSolutionState;
import tech.provve.statemachine.domain.value.SaveSkillState;
import tech.provve.statemachine.exception.StatemachineAlreadyExists;
import tech.provve.task.Scheduling;
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
    public static Consumer<String> delayedSkillVoteCreator = delayedVoteJson -> {
        try {
            var deadline = LocalDateTime.now(ZoneOffset.UTC)
                                        .plusMonths(1);
            Map<String, Object> vote = Jackson.json.readValue(delayedVoteJson, new TypeReference<Map<String, Object>>() {
            });
            vote.put(Entity.Vote.DEADLINE, deadline);
            Storage.saveVote(vote);

            String voteName = Collection.get(vote, Entity.Vote.NAME);

            Scheduling.addSkill(voteName,
                                deadline.toInstant(ZoneOffset.UTC));
            Account.notifyVoteStarted(voteName, voteName);
        } catch (DatabindException e) {
            throw new RuntimeException("Couldn't create Vote from given json:" + e);
        }
    };

    public static BiConsumer<String, String> validationErrorNotificationSender = (skill, author) -> {
        String email = Storage.findAccountByLogin(author)
                              .map(account -> Collection.<String>getOrNull(account, Entity.Account.EMAIL))
                              .orElse(null);
        NotificationSending.send(
                NotificationSending.authoredSkillNotSavedCommand(author, email),
                NotificationSending.fillAuthoredSkillNotSavedTemplate(author, skill)
        );
    };

    public static BiConsumer<String, String> skillSavedNotificationSender = (skill, author) -> {
        String email = Storage.findAccountByLogin(author)
                              .map(account -> Collection.<String>getOrNull(account, Entity.Account.EMAIL))
                              .orElse(null);
        NotificationSending.send(
                NotificationSending.authoredSkillSavedCommand(author, email),
                NotificationSending.fillAuthoredSkillSavedTemplate(author, skill)
        );
    };

    public static void continueAll() {
        Storage.listSaveSkills()
               .stream()
               .filter(s -> !SaveSkillState.PREPARED.equals(Collection.get(s, Entity.SaveSkill.STATE)))
               .forEach(saveSkill -> {

                   var s = new SaveSkillMachine();
                   s.setInitialState(Collection.get(saveSkill, Entity.SaveSkill.STATE));
                   s.init(Collection.get(saveSkill, Entity.SaveSkill.NAME),
                          Collection.get(saveSkill, Entity.SaveSkill.AUTHOR),
                          Collection.get(saveSkill, Entity.SaveSkill.DELAYED_VOTE_JSON));
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

    public static void createSaveSkill(String name, String author, String delayedVoteJson) throws StatemachineAlreadyExists {
        if (Storage.saveSkillExists(name)) {
            throw new StatemachineAlreadyExists(name);
        }
        Map<String, Object> saveSkill = new HashMap<>();
        saveSkill.put(Entity.SaveSkill.NAME, name);
        saveSkill.put(Entity.SaveSkill.STATE, SaveSkillState.UNPREPARED);
        saveSkill.put(Entity.SaveSkill.AUTHOR, author);
        saveSkill.put(Entity.SaveSkill.DELAYED_VOTE_JSON, delayedVoteJson);
        createSaveSkill(saveSkill);
    }

    private static void createSaveSkill(Map<String, Object> saveSkill) {
        var s = saveSkillMachine();
        s.setInitialState(Collection.get(saveSkill, Entity.SaveSkill.STATE));
        s.init(Collection.get(saveSkill, Entity.SaveSkill.NAME),
               Collection.get(saveSkill, Entity.SaveSkill.AUTHOR),
               Collection.get(saveSkill, Entity.SaveSkill.DELAYED_VOTE_JSON));
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

    private static SaveSkillMachine saveSkillMachine() {
        return new SaveSkillMachine();
    }

    private static CheckSolutionMachine checkSolutionMachine() {
        return new CheckSolutionMachine();
    }

}
