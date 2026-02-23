package tech.provve.skill.service.application;

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
        var vote = new Vote(
                name,
                true,
                false,
                "",
                LocalDateTime.now(),
                "",
                Vote.Type.ADD_SKILL,
                tags,
                null,
                null
        );

        // act
        skillService.create(vote);

        // assert
        verify(skillRepository).save(new Skill(vote.name(), vote.tags()));
    }

}