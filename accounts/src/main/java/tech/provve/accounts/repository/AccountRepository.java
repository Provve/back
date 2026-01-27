package tech.provve.accounts.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.mapper.AccountMapper;

import java.sql.Connection;
import java.util.Optional;

import static tech.provve.accounts.db.generated.tables.Accounts.ACCOUNTS_;

@Singleton
@RequiredArgsConstructor
public class AccountRepository {

    @External
    private final Connection connection;

    private final RecordMapper<Record, Account> accountRecordMapper = result ->
            new Account(
                    result.get(ACCOUNTS_.LOGIN),
                    result.get(ACCOUNTS_.EMAIL),
                    result.get(ACCOUNTS_.PASSWORD_HASH),
                    result.get(ACCOUNTS_.CONSENT_PERSONAL_DATA),
                    result.get(ACCOUNTS_.USERNAME),
                    result.get(ACCOUNTS_.AVATAR_URL),
                    result.get(ACCOUNTS_.PREMIUM)
            );

    /**
     * Save completely new account.
     */
    public void save(Account account) {
        DSL.using(connection, SQLDialect.POSTGRES)
           .insertInto(ACCOUNTS_)
           .set(AccountMapper.INSTANCE.map(account))
           .execute();
    }

    public Optional<Account> findByLogin(String login) {
        return DSL.using(connection, SQLDialect.POSTGRES)
                  .select()
                  .from(ACCOUNTS_)
                  .where(ACCOUNTS_.LOGIN.eq(login))
                  .fetchOptional(accountRecordMapper);
    }

    public Optional<Account> findByEmail(String email) {
        return DSL.using(connection, SQLDialect.POSTGRES)
                  .select()
                  .from(ACCOUNTS_)
                  .where(ACCOUNTS_.EMAIL.eq(email))
                  .fetchOptional(accountRecordMapper);
    }

    public void updatePasswordHash(String passwordHash, String login) {
        DSL.using(connection, SQLDialect.POSTGRES)
           .update(ACCOUNTS_)
           .set(ACCOUNTS_.PASSWORD_HASH, passwordHash)
           .where(ACCOUNTS_.LOGIN.eq(login))
           .execute();
    }
}
