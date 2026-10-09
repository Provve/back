package tech.provve.skill.domain;

import alekseyvideman.dop.Collection;
import tech.provve.api.generated.dto.CollectionRequest;
import tech.provve.api.generated.dto.Cursor;
import tech.provve.api.generated.dto.SkillResponse;
import tech.provve.api.generated.dto.Skills;
import tech.provve.constants.Entity;
import tech.provve.util.Storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Skill {

    public static void createFrom(Map<String, Object> fromVote) {
        Map<String, Object> skill = new HashMap<>();
        skill.put(Entity.Skill.NAME, Collection.get(fromVote, Entity.Vote.NAME));
        skill.put(Entity.Skill.TAGS, Collection.get(fromVote, Entity.Vote.TAGS));

        Map<String, Object> skillAddVote = Collection.get(fromVote, Entity.Vote.SKILL);
        skill.put(Entity.Skill.DESCRIPTION, Collection.get(skillAddVote, Entity.Skill.DESCRIPTION));
        skill.put(Entity.Skill.PRIVATE_ARCHIVE_URL, Collection.get(skillAddVote, Entity.Skill.PRIVATE_ARCHIVE_URL));
        skill.put(Entity.Skill.PUBLIC_ARCHIVE_URL, Collection.get(skillAddVote, Entity.Skill.PUBLIC_ARCHIVE_URL));

        Storage.saveSkill(skill);
    }

    public static Skills list(CollectionRequest request) {
        List<SkillResponse> all = Storage.getAllSkills(request.getFilter(),
                                                       request.getPagination()
                                                              .getPrevious(),
                                                       request.getPagination()
                                                              .getSize())
                                         .stream()
                                         .map(skill -> new SkillResponse(
                                                 Collection.get(skill, Entity.Skill.NAME),
                                                 Collection.getOrNull(skill, Entity.Skill.DESCRIPTION),
                                                 Collection.getOrNull(skill, Entity.Skill.PUBLIC_ARCHIVE_URL),
                                                 Collection.get(skill, Entity.Skill.TAGS)))
                                         .toList();
        if (all.isEmpty()) {
            return new Skills(all, new Cursor(""));
        }

        var cursor = new Cursor(all.getLast()
                                   .getName());
        return new Skills(all, cursor);
    }
}
