package tech.provve.accounts.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import tech.provve.accounts.db.generated.tables.records.PremiumExpirationRecord;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.domain.model.value.PremiumExpiration;
import tech.provve.accounts.mapper.AccountMapper;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static tech.provve.accounts.db.generated.Routines.expirePremiumAccounts;
import static tech.provve.accounts.db.generated.tables.Accounts.ACCOUNTS_;
import static tech.provve.accounts.db.generated.tables.PremiumExpiration.PREMIUM_EXPIRATION;

@Singleton
@RequiredArgsConstructor
public class AccountRepository {

    @External
    private final DSLContext dsl;

    private final RecordMapper<Record, Account> outputMapper = result ->
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
        dsl.insertInto(ACCOUNTS_)
           .set(AccountMapper.INSTANCE.map(account))
           .execute();
    }

    public Optional<Account> findByLogin(String login) {
        return dsl.select()
                  .from(ACCOUNTS_)
                  .where(ACCOUNTS_.LOGIN.eq(login))
                  .fetchOptional(outputMapper);
    }

    public Optional<Account> findByEmail(String email) {
        return dsl.select()
                  .from(ACCOUNTS_)
                  .where(ACCOUNTS_.EMAIL.eq(email))
                  .fetchOptional(outputMapper);
    }

    public void updatePasswordHash(String passwordHash, String login) {
        dsl.update(ACCOUNTS_)
           .set(ACCOUNTS_.PASSWORD_HASH, passwordHash)
           .where(ACCOUNTS_.LOGIN.eq(login))
           .execute();
    }

    public void updateEmail(String login, String email) {
        dsl.update(ACCOUNTS_)
           .set(ACCOUNTS_.EMAIL, email)
           .where(ACCOUNTS_.LOGIN.eq(login))
           .execute();
    }

    public void updateAvatarUrl(String login, String avatarUrl) {
        dsl.update(ACCOUNTS_)
           .set(ACCOUNTS_.AVATAR_URL, avatarUrl)
           .where(ACCOUNTS_.LOGIN.eq(login))
           .execute();
    }

    public void updatePremium(String login, boolean premium) {
        dsl.update(ACCOUNTS_)
           .set(ACCOUNTS_.PREMIUM, premium)
           .where(ACCOUNTS_.LOGIN.eq(login))
           .execute();
    }

    public void updatePersonalDataConsent(String login, boolean personalDataConsent) {
        dsl.update(ACCOUNTS_)
           .set(ACCOUNTS_.CONSENT_PERSONAL_DATA, personalDataConsent)
           .where(ACCOUNTS_.LOGIN.eq(login))
           .execute();
    }

    /**
     * @return аккаунты с просроченным и уже (!) отключенным premium
     */
    public List<Account> findPremiumExpired() {
        return expirePremiumAccounts(dsl.configuration())
                .map(outputMapper);
    }

    public void clearPremiumExpired() {
        dsl.deleteFrom(PREMIUM_EXPIRATION)
           .where(PREMIUM_EXPIRATION.EXPIRY.lessOrEqual(OffsetDateTime.now(ZoneId.of("UTC"))))
           .execute();
    }

    public void save(PremiumExpiration premiumExpiration) {
        dsl.insertInto(PREMIUM_EXPIRATION)
           .set(
                   new PremiumExpirationRecord(premiumExpiration.login(), premiumExpiration.expiry())
           )
           .execute();
    }

}
