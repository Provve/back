package tech.provve.accounts.repository;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.mapper.AccountMapper;

import java.sql.Connection;

import static tech.provve.accounts.db.generated.tables.Accounts.ACCOUNTS_;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class AccountRepository {

    private final Connection connection;

    /**
     * Save completely new account.
     */
    public void save(Account account) {
        DSLContext dslContext = DSL.using(connection, SQLDialect.POSTGRES);
        dslContext.insertInto(ACCOUNTS_)
                  .set(AccountMapper.INSTANCE.map(account))
                  .execute();
    }
}
