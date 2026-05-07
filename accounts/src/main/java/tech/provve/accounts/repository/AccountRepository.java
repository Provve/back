package tech.provve.accounts.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.mapper.AccountMapper;

import java.util.List;
import java.util.Optional;

import static tech.provve.accounts.db.generated.tables.Accounts.ACCOUNTS_;

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
                    result.get(ACCOUNTS_.CONTACT_INFO),
                    List.of(result.get(ACCOUNTS_.INTERESTS)),
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

    /**
     * Delete row and all cascade tables, possibly
     */
    public void delete(String login) {
        dsl.deleteFrom(ACCOUNTS_)
           .where(ACCOUNTS_.LOGIN.eq(login))
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

    @SuppressWarnings("all")
    public List<Account> findInterestedIn(String skillName) {
        var select = dsl.select()
                        .from(ACCOUNTS_)
                        .where(ACCOUNTS_.INTERESTS.contains(new String[]{skillName}));
        return dsl.fetchMany(select)
                  .stream()
                  .map(result -> result.map(outputMapper))
                  .findAny()
                  .get();
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

    public void updateContactInfo(String login, String contactInfo) {
        dsl.update(ACCOUNTS_)
           .set(ACCOUNTS_.CONTACT_INFO, contactInfo)
           .where(ACCOUNTS_.LOGIN.eq(login))
           .execute();
    }

    public void updateInterests(String login, List<String> interests) {
        dsl.update(ACCOUNTS_)
           .set(ACCOUNTS_.INTERESTS, interests.toArray(new String[0]))
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

}
