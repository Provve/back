package tech.provve.accounts.repository;

import jakarta.inject.Inject;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.junit.jupiter.api.Test;
import tech.provve.accounts.IntegrationTest;
import tech.provve.accounts.domain.model.Account;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest extends IntegrationTest {

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