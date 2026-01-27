package tech.provve.accounts.repository;

import io.avaje.inject.BeanScopeBuilder;
import io.avaje.inject.test.InjectTest;
import io.avaje.inject.test.Setup;
import jakarta.inject.Inject;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.junit.jupiter.api.Test;
import tech.provve.accounts.PostgresIntegrationTest;
import tech.provve.accounts.domain.model.Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@InjectTest
class AccountRepositoryTest extends PostgresIntegrationTest {

    @Setup
    void set(BeanScopeBuilder b) {
        b.bean(Connection.class, connection());
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
    void findByLogin_savedCorrectly_returnedSame() {
        // arrange
        var account = new Account(
                "b",
                "b@c.d",
                "h",
                true,
                "n",
                "p",
                true
        );
        repository.save(account);

        // act
        var returned = repository.findByLogin(account.login());

        // assert
        assertEquals(account, returned.get());
    }

    @Test
    void save_missingRequiredValues_throwsException() {
        // arrange
        var account = new Account(
                "a",
                "",
                "h",
                true,
                "n",
                "p",
                false
        );

        // act assert
        assertThrows(IntegrityConstraintViolationException.class, () -> repository.save(account));
    }

}