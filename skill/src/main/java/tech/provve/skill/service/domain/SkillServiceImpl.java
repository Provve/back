package tech.provve.skill.service.domain;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.api.server.generated.dto.CollectionRequest;
import tech.provve.api.server.generated.dto.Cursor;
import tech.provve.api.server.generated.dto.SkillResponse;
import tech.provve.api.server.generated.dto.Skills;
import tech.provve.skill.domain.entity.Skill;
import tech.provve.skill.domain.entity.Vote;
import tech.provve.skill.repository.SkillRepository;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    public void createFrom(Vote fromVote) {
        skillRepository.save(new Skill(fromVote.getName(), fromVote.getTags()));
    }

    @Override
    public Skills list(CollectionRequest request) {
        List<SkillResponse> all = skillRepository.getAll(request.getFilter(),
                                                         request.getPagination()
                                                                .getPrevious(),
                                                         request.getPagination()
                                                                .getSize())
                                                 .stream()
                                                 .map(skill -> new SkillResponse(skill.name(), skill.tags()))
                                                 .toList();
        if (all.isEmpty()) {
            return new Skills(all, new Cursor(""));
        }
        ;

        var cursor = new Cursor(all.getLast()
                                   .getName());
        return new Skills(all, cursor);
    }
}
