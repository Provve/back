package tech.provve.statemachine.domain.entity;

import tech.provve.statemachine.domain.value.CheckSolutionState;

/**
 * @param examinee автор решения
 */
public record CheckSolution(String name, CheckSolutionState state, String examinee) {

}
