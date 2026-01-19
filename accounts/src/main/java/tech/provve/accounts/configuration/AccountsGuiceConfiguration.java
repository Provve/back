package tech.provve.accounts.configuration;

import com.google.inject.AbstractModule;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.accounts.service.application.AccountService;
import tech.provve.accounts.service.application.AccountServiceImpl;

public class AccountsGuiceConfiguration extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountRepository.class);
        bind(AccountService.class).to(AccountServiceImpl.class);
    }
}
