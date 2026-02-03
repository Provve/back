package tech.provve.accounts.task;

import jakarta.inject.Singleton;
import tech.provve.accounts.service.application.AccountService;

@Singleton
public class DowngradeExpiredPremium implements Runnable {

    private final AccountService accountService;

    public DowngradeExpiredPremium(AccountService service) {
        accountService = service;
    }

    @Override
    public void run() {
        accountService.downgradeAllExpired();
    }
}
