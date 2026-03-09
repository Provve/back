package tech.provve.statemachine.domain.entity;

import tech.provve.statemachine.domain.value.SaveExamState;

public record SaveExam(String name, SaveExamState state, String author, String delayedVoteJson) {

}
