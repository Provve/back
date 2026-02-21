package tech.provve.skill.service.application;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.skill.domain.entity.Skill;
import tech.provve.skill.repository.SkillRepository;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    public void create(String name, List<String> tags) {
        skillRepository.save(new Skill(name, tags));
    }
}
