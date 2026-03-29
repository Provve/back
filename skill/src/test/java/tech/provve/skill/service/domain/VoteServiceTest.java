package tech.provve.skill.service.domain;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.skill.domain.entity.Vote;
import tech.provve.skill.domain.value.VoteReactions;
import tech.provve.skill.repository.SkillRepository;
import tech.provve.skill.repository.VoteRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@InjectTest
class VoteServiceTest {

    @Mock(stubOnly = true)
    SkillRepository skillRepository;

    @Mock(stubOnly = true)
    JwsParsingService jwsParsingService;

    @Mock
    VoteRepository voteRepository;

    @Inject
    VoteService voteService;

    @ParameterizedTest
    @CsvSource("""
            1, 0
            11, 3
            """)
    void end_morePositiveReactions_successTrue(int positive, int negaitve) {
        // arrange
        var vote = Vote.builder()
                       .name("v")
                       .active(true)
                       .success(false)
                       .author("")
                       .deadline(LocalDateTime.now())
                       .arguments("")
                       .type(Vote.Type.ADD_SKILL)
                       .tags(emptyList())
                       .reactions(new VoteReactions(positive, negaitve))
                       .build();
        when(voteRepository.findByName(anyString())).thenReturn(Optional.of(vote));

        // act
        boolean success = voteService.end("v");


        // assert
        assertThat(success).isTrue();
    }

    @ParameterizedTest
    @CsvSource("""
            0, 1
            3, 11
            """)
    void end_moreNegativeReactions_successFalse(int positive, int negaitve) {
        // arrange
        var vote = Vote.builder()
                       .name("v")
                       .active(true)
                       .success(false)
                       .author("")
                       .deadline(LocalDateTime.now())
                       .arguments("")
                       .type(Vote.Type.ADD_SKILL)
                       .tags(emptyList())
                       .reactions(new VoteReactions(positive, negaitve))
                       .build();
        when(voteRepository.findByName(anyString())).thenReturn(Optional.of(vote));

        // act
        boolean success = voteService.end("v");


        // assert
        assertThat(success).isFalse();
    }

}
