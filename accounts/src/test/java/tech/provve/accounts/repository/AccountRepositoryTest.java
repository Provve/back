package tech.provve.accounts.repository;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import name.falgout.jeffrey.testing.junit.guice.GuiceExtension;
import name.falgout.jeffrey.testing.junit.guice.IncludeModule;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import tech.provve.accounts.configuration.AccountsGuiceConfiguration;
import tech.provve.accounts.domain.model.Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(GuiceExtension.class)
@Testcontainers

@IncludeModule(AccountsGuiceConfiguration.class)
@IncludeModule(AccountRepositoryTest.TestConnection.class)
class AccountRepositoryTest {

    @Container
    static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:latest")
            .withInitScripts("db/migration/V1__Init.sql")
            .withUsername("postgres")
            .withPassword("1");

    @Inject
    AccountRepository repository;

    @Test
    void save_allValuesSet_savedWithoutExceptions() {
        // arrange
        var account = new Account(
                "a",
                "b@c.d",
                "h",
                true,
                "n",
                "p",
                false
        );

        // act assert
        assertDoesNotThrow(() -> repository.save(account));
    }

    @Test
    void save_missingRequiredValues_throwsException() {
        // arrange
        var account = new Account(
                null,
                "b@c.d",
                "h",
                true,
                "n",
                "p",
                false
        );

        // act assert
        assertThrows(IntegrityConstraintViolationException.class, () -> repository.save(account));
    }

    static class TestConnection extends AbstractModule {

        @Provides
        Connection connection() throws SQLException {
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "1");

            return DriverManager.getConnection(postgres.getJdbcUrl(), props);
        }

    }

}