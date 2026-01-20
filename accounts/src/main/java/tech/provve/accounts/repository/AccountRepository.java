package tech.provve.accounts.repository;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
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

    private final Connection connection;

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
                  .fetchOptional(result -> result.map(result1 ->
                                                              new Account(
                                                                      result1.get(ACCOUNTS_.LOGIN),
                                                                      result1.get(ACCOUNTS_.EMAIL),
                                                                      result1.get(ACCOUNTS_.PASSWORD_HASH),
                                                                      result1.get(ACCOUNTS_.CONSENT_PERSONAL_DATA),
                                                                      result1.get(ACCOUNTS_.USERNAME),
                                                                      result1.get(ACCOUNTS_.AVATAR_URL),
                                                                      result1.get(ACCOUNTS_.PREMIUM)
                                                              )));
    }
}
