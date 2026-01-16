package tech.provve.accounts.repository;

import tech.provve.accounts.domain.model.Account;

public interface AccountRepository {

    void save(Account account);

}
