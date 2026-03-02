package tech.provve.skill.service.application;

import tech.provve.skill.domain.entity.Exam;
import tech.provve.skill.domain.entity.Vote;

public interface SkillService {

    void create(Vote fromVote);

    void addExam(String toSkill, Exam exam);

}
