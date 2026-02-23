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
import tech.provve.skill.PostgresIntegrationTest;
import tech.provve.skill.domain.entity.ExamAddVote;
import tech.provve.skill.domain.entity.Skill;
import tech.provve.skill.domain.entity.Vote;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.provve.skill.domain.entity.Vote.Type.ADD_EXAM;
import static tech.provve.skill.domain.entity.Vote.Type.DELETE_SKILL;

@InjectTest
class VoteRepositoryTest extends PostgresIntegrationTest {

    @Setup
    void set(BeanScopeBuilder b) {
        b.bean(DSLContext.class, DSL.using(connection(), SQLDialect.POSTGRES));
    }

    Connection connection() {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "1");

        try {
            return DriverManager.getConnection(postgres.getJdbcUrl(), props);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        voteRepository.save(new Vote(
                author_login, true, false, author_login, LocalDateTime.now(), "xyz!", Vote.Type.ADD_SKILL, List.of("1"), null, null
        ));

        // assert
        var savedVote = voteRepository.findByName(author_login)
                                      .get();
        assertThat(savedVote.name()).isEqualTo(author_login);
    }

    @Test
    void save_type_examAddVode_saved() {
        // arrange
        var author_login = "a";
        accountRepository.save(new Account(author_login, "a", "1", true, "q", null, null, false));

        skillRepository.save(new Skill(author_login, List.of("a")));
        var description = "d";

        // act
        voteRepository.save(new Vote(
                author_login,
                true,
                false,
                author_login,
                LocalDateTime.now(),
                "xyz!",
                ADD_EXAM,
                List.of("1"),
                new ExamAddVote("a", description, ""),
                null
        ));

        // assert
        var savedVote = voteRepository.findByName(author_login)
                                      .get();
        assertThat(savedVote.examAddVote()
                            .description()).isEqualTo(description);
    }

    @Test
    void findAll_differentVotesPresented_returnedAllVotes() {
        // arrange
        var author_login = "a";
        accountRepository.save(new Account(author_login, "a", "1", true, "q", null, null, false));
        skillRepository.save(new Skill(author_login, List.of("a")));

        var examAddvote = new ExamAddVote("a", "d", "");
        var now = LocalDateTime.now();
        var votes = List.of(
                new Vote(
                        "a",
                        true,
                        false,
                        "a",
                        now,
                        "xyz!",
                        ADD_EXAM,
                        List.of("1"),
                        examAddvote,
                        null
                ), new Vote(
                        "b",
                        true,
                        false,
                        "a",
                        now,
                        "xyz!",
                        DELETE_SKILL,
                        List.of("1"),
                        examAddvote,
                        null
                )
        );

        // act
        votes.forEach(voteRepository::save);

        // assert
        var savedVotes = voteRepository.getAll();
        assertThat(savedVotes).satisfiesExactly(
                vote -> ADD_EXAM.equals(vote.type()),
                vote -> DELETE_SKILL.equals(vote.type())
        );
    }

}