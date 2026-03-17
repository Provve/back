package tech.provve.skill.service.domain;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import tech.provve.skill.domain.entity.Skill;
import tech.provve.skill.domain.entity.Vote;
import tech.provve.skill.repository.SkillRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;

@InjectTest
class SkillServiceTest {

    @Mock
    SkillRepository skillRepository;

    @Inject
    SkillService skillService;

    @Test
    void create_givenVote_createdSkillWithNameFromVote() {
        // arrange
        var name = "v";
        var tags = List.of("x", "y");
        var vote = Vote.builder()
                       .name(name)
                       .active(true)
                       .success(false)
                       .author("")
                       .deadline(LocalDateTime.now())
                       .arguments("")
                       .type(Vote.Type.ADD_SKILL)
                       .tags(tags)
                       .build();

        // act
        skillService.create(vote);

        // assert
        verify(skillRepository).save(new Skill(vote.getName(), vote.getTags()));
    }

}