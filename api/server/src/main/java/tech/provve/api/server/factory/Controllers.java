package tech.provve.api.server.factory;

import io.avaje.inject.Bean;
import io.avaje.inject.External;
import io.avaje.inject.Factory;
import tech.provve.accounts.service.application.AccountService;
import tech.provve.api.server.controller.AccountsController;
import tech.provve.api.server.generated.api.AccountsApi;

@Factory
public class Controllers {

    @Bean
    public AccountsApi accountsApi(@External AccountService accountService) {
        return new AccountsController(accountService); // не вяжется через аннотации
    }

}
