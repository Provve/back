package tech.provve.accounts.repository;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.junit.jupiter.api.Test;
import tech.provve.accounts.PostgresIntegrationTest;
import tech.provve.accounts.domain.model.Account;

import static org.junit.jupiter.api.Assertions.*;

@InjectTest
class AccountRepositoryTest extends PostgresIntegrationTest {

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