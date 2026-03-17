package tech.provve.skill.service.domain;

import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.Setup;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.api.server.generated.dto.CastVoteRequest;
import tech.provve.skill.PostgresIntegrationTest;
import tech.provve.skill.domain.entity.Vote;
import tech.provve.skill.exception.AuthorCannotVote;
import tech.provve.skill.exception.CastAlreadyExists;
import tech.provve.skill.repository.SkillRepository;
import tech.provve.skill.repository.VoteRepository;

import java.time.LocalDateTime;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@InjectTest
public class VoteService_VoteRepository__IT extends PostgresIntegrationTest {

    @Setup
    void set(BeanScopeBuilder b) {
        b.bean(DSLContext.class, DSL.using(connection(), SQLDialect.POSTGRES));
    }

    @Mock(stubOnly = true)
    SkillRepository skillRepository;

    @Mock(stubOnly = true)
    JwsParsingService jwsParsingService;

    @Inject
    DSLContext c;

    @Inject
    AccountRepository accountRepository;

    @Inject
    VoteRepository voteRepository;

    @Inject
    VoteService voteService;

    @Test
    void cast_voterIsAuthor_exception() {
        // arrange
        var author = "r";
        accountRepository.save(new Account(author, "", "", false, "", "", "", false));

        var vote = Vote.builder()
                       .name("v")
                       .active(true)
                       .success(false)
                       .author(author)
                       .deadline(LocalDateTime.now())
                       .arguments("")
                       .type(Vote.Type.ADD_SKILL)
                       .tags(emptyList())
                       .build();
        voteRepository.save(vote);

        when(jwsParsingService.parseAuth(any(), eq("sub"))).thenReturn(author);

        // act assert
        assertThatThrownBy(() -> {
            voteService.cast(vote.getName(), new CastVoteRequest(true, ""));
        }).isExactlyInstanceOf(AuthorCannotVote.class);
    }

    @Test
    void cast_doubleVote_exception() {
        // arrange
        var author = "g";
        accountRepository.save(new Account(author, "", "", false, "", "", "", false));

        var notAuthor = "r";
        accountRepository.save(new Account(notAuthor, "", "", false, "", "", "", false));

        var vote = Vote.builder()
                       .name("v")
                       .active(true)
                       .success(false)
                       .author(author)
                       .deadline(LocalDateTime.now())
                       .arguments("")
                       .type(Vote.Type.ADD_SKILL)
                       .tags(emptyList())
                       .build();
        voteRepository.save(vote);

        when(jwsParsingService.parseAuth(any(), eq("sub"))).thenReturn(notAuthor);

        // act assert
        assertThatThrownBy(() -> {
            voteService.cast(vote.getName(), new CastVoteRequest(true, ""));
            voteService.cast(vote.getName(), new CastVoteRequest(true, ""));
        }).isExactlyInstanceOf(CastAlreadyExists.class);
    }

    @Test
    void cast_positive_saved() {
        // arrange
        var author = "g";
        accountRepository.save(new Account(author, "", "", false, "", "", "", false));

        var notAuthor = "r";
        accountRepository.save(new Account(notAuthor, "", "", false, "", "", "", false));

        var vote = Vote.builder()
                       .name("v")
                       .active(true)
                       .success(false)
                       .author(author)
                       .deadline(LocalDateTime.now())
                       .arguments("")
                       .type(Vote.Type.ADD_SKILL)
                       .tags(emptyList())
                       .build();
        voteRepository.save(vote);

        when(jwsParsingService.parseAuth(any(), eq("sub"))).thenReturn(notAuthor);

        // act
        voteService.cast(vote.getName(), new CastVoteRequest(true, ""));

        // assert
        assertThat(voteRepository.findByName(vote.getName())
                                 .get()
                                 .getReactions()
                                 .positive()).isEqualTo(1);
    }

}
