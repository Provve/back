package tech.provve.skill.repository;

import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.Setup;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.skill.PostgresIntegrationTest;
import tech.provve.skill.domain.entity.Exam;
import tech.provve.skill.domain.entity.Skill;
import tech.provve.skill.domain.entity.Vote;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static tech.provve.skill.domain.entity.Vote.Type.ADD_EXAM;
import static tech.provve.skill.domain.entity.Vote.Type.DEL_SKILL;

@InjectTest
class VoteRepositoryTest extends PostgresIntegrationTest {

    @Setup
    void set(BeanScopeBuilder b) {
        b.bean(DSLContext.class, DSL.using(connection(), SQLDialect.POSTGRES));
    }

    @Inject
    DSLContext c;

    @Inject
    AccountRepository accountRepository;

    @Inject
    VoteRepository voteRepository;

    @Inject
    SkillRepository skillRepository;

    @Test
    void save_type_notExamAddVote_saved() {
        // arrange
        var author_login = "a";
        accountRepository.save(new Account(author_login, "a", "1", true, "q", null, null, false));

        // act
        voteRepository.save(Vote.builder()
                                .name(author_login)
                                .active(true)
                                .success(false)
                                .author(author_login)
                                .deadline(LocalDateTime.now())
                                .arguments("xyz!")
                                .type(Vote.Type.ADD_SKILL)
                                .tags(List.of("1"))
                                .build());

        // assert
        var savedVote = voteRepository.findByName(author_login)
                                      .get();
        assertThat(savedVote.getName()).isEqualTo(author_login);
    }

    @Test
    void save_type_examAddVode_saved() {
        // arrange
        var author_login = "aa";
        accountRepository.save(new Account(author_login, "a", "1", true, "q", null, null, false));

        var skill = new Skill("a", List.of("a"));
        skillRepository.save(skill);
        var description = "d";

        // act
        voteRepository.save(Vote.builder()
                                .name(author_login)
                                .active(true)
                                .success(false)
                                .author(author_login)
                                .deadline(LocalDateTime.now())
                                .arguments("xyz!")
                                .type(Vote.Type.ADD_EXAM)
                                .tags(List.of("1"))
                                .exam(new Exam("b", skill.name(), description, "", ""))
                                .build());

        // assert
        var savedVote = voteRepository.findByName(author_login)
                                      .get();
        assertThat(savedVote.getExam()
                            .description()).isEqualTo(description);
    }

    @Test
    void findAll_differentVotesPresented_returnedAllVotes() {
        // arrange
        var author_login = "aaa";
        accountRepository.save(new Account(author_login, "a", "1", true, "q", null, null, false));
        skillRepository.save(new Skill(author_login, List.of("a")));

        var examAddvote = new Exam("b", "a", "d", "", "");
        var now = LocalDateTime.now();
        var votes = List.of(
                Vote.builder()
                    .name("8734")
                    .active(true)
                    .success(false)
                    .author("a")
                    .deadline(now)
                    .arguments("xyz!")
                    .type(Vote.Type.ADD_EXAM)
                    .tags(List.of("1"))
                    .exam(examAddvote)
                    .build(),
                Vote.builder()
                    .name("b")
                    .active(true)
                    .success(false)
                    .author("a")
                    .deadline(now)
                    .arguments("xyz!")
                    .type(Vote.Type.DEL_SKILL)
                    .tags(List.of("1"))
                    .exam(examAddvote)
                    .build()
        );

        // act
        votes.forEach(voteRepository::save);

        // assert
        var savedVotes = voteRepository.getAll(new Filter(emptyList()), "", 1);
        assertThat(savedVotes).extracting(Vote::getType)
                              .anyMatch(ADD_EXAM::equals)
                              .anyMatch(DEL_SKILL::equals);
    }

}