package tech.provve.skill.service.application;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.skill.domain.entity.Exam;
import tech.provve.skill.domain.entity.Skill;
import tech.provve.skill.domain.entity.Vote;
import tech.provve.skill.repository.SkillRepository;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    public void create(Vote fromVote) {
        skillRepository.save(new Skill(fromVote.getName(), fromVote.getTags()));
    }

    @Override
    public void addExam(String toSkill, Exam exam) {

    }
}
