package tech.provve.skill.service.application;

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
import tech.provve.api.server.generated.dto.SkillAddVote;
import tech.provve.skill.PostgresIntegrationTest;
import tech.provve.skill.repository.SkillRepository;
import tech.provve.skill.repository.VoteRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@InjectTest
class VoteService_SkillRepository__IT extends PostgresIntegrationTest {

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

    @Mock
    JwsParsingService jwsParsingService;

    @Mock
    Supplier<LocalDateTime> deadlineSupplier = () -> LocalDateTime.now()
                                                                  .minusSeconds(1);

    @Inject
    SkillRepository skillRepository;

    @Inject
    VoteService voteService;

    @Inject
    VoteRepository voteRepository;

    @Inject
    AccountRepository accountRepository;

    @Test
    void create_skillAdd_deadlineReached_skillCreated() {
        // arrange
        var author = "c";
        accountRepository.save(new Account(
                author,
                "b@c.d",
                "h",
                true,
                "n",
                "p",
                null,
                false
        ));

        var voteName = "x";
        var authToken = "a";
        when(jwsParsingService.parseAuth(authToken)).thenReturn(Map.of("sub", author));
        voteService.create(new SkillAddVote(voteName, "", emptyList(), authToken));

        // act
        var createdSkill = skillRepository.findByName(voteName)
                                          .get();

        /*
        через deadline нужно вытащить vote, создать по нему skill
        удалить фоновую задачу (надеюсь это делает сам db scheduler)
         */

        // assert
        assertThat(createdSkill.name()).isEqualTo(voteName);
    }

}