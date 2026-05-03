package tech.provve.statemachine.domain.entity;

import tech.provve.statemachine.domain.value.CheckSolutionState;

/**
 * @param examinee автор решения
 * @param name название экзамена, для которого сделано решение
 */
public record CheckSolution(String name, CheckSolutionState state, String examinee) {

}
