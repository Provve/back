package tech.provve.statemachine;

import de.amr.statemachine.Match;
import de.amr.statemachine.StateMachine;
import jakarta.inject.Singleton;
import tech.provve.statemachine.domain.entity.CheckSolution;
import tech.provve.statemachine.domain.value.CheckSolutionEvent;
import tech.provve.statemachine.domain.value.CheckSolutionState;
import tech.provve.statemachine.repository.CheckSolutionRepository;

import static tech.provve.statemachine.domain.value.CheckSolutionEvent.*;
import static tech.provve.statemachine.domain.value.CheckSolutionState.*;

/**
 * МС для проверки решения от экзаменуемого
 */
@Singleton
public class CheckSolutionMachine extends StateMachine<CheckSolutionState, CheckSolutionEvent> {

    private final CheckSolutionRepository repository;

    public CheckSolutionMachine(CheckSolutionRepository repository) {
        super(CheckSolutionState.class, Match.BY_EQUALITY);
        this.repository = repository;
    }

    public void init(String name, String examinee) {
        //@formatter:off
        beginStateMachine()
                .description("Check Solution")
                .initialState(UNPREPARED)
                .states()
                    .state(UNPREPARED)
                        .onEntry(() -> {
                            repository.save(new CheckSolution(name, getState(), examinee));
                        })
                        .onExit(() -> {

                        })
                    .state(PREPARED)
                        .onEntry(() -> {

                            repository.updateState(name, getState());
                        })
                    .state(RUNNING)
                        .onEntry(() -> {

                            repository.updateState(name, getState());
                        })
                    .state(STOPPED)
                        .onEntry(() -> {

                            repository.updateState(name, getState());
                        })
                    .state(CRASHED)
                .transitions()
                    .when(UNPREPARED).then(PREPARED).on(PREPARE)
                    .when(PREPARED).then(RUNNING).on(RUN)
                    .when(RUNNING).then(STOPPED).on(STOP)
                    .when(RUNNING).then(CRASHED).on(CRASH)
        .endStateMachine();
        //@formatter:on

        super.init();
    }

}
