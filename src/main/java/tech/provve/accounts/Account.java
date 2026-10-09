package tech.provve.accounts;

import alekseyvideman.dop.Collection;
import io.avaje.config.Config;
import lombok.SneakyThrows;
import tech.provve.accounts.exception.*;
import tech.provve.api.generated.dto.*;
import tech.provve.constants.Entity;
import tech.provve.notification.NotificationSending;
import tech.provve.task.Scheduling;
import tech.provve.util.Jackson;
import tech.provve.util.S3;
import tech.provve.util.Storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNullElseGet;
import static tech.provve.accounts.JwsParsing.JWT_SUBJECT;
import static tech.provve.notification.NotificationSending.*;

/**
 * Прикладной сервис управления аккаунтами.
 */
public class Account {

    public static void register(RegisterAccountRequest registerAccountRequest) {
        Storage.findAccountByLogin(registerAccountRequest.getLogin())
               .ifPresent(_ -> {
                   throw new AccountAlreadyExists("Account with login '%s' is already exists.".formatted(
                           registerAccountRequest.getLogin()));
               });
        Storage.findAccountByEmail(registerAccountRequest.getEmail())
               .ifPresent(_ -> {
                   throw new AccountAlreadyExists("Account with email '%s' is already exists.".formatted(
                           registerAccountRequest.getEmail()));
               });

        Map<String, Object> account = new HashMap<>();
        account.put(Entity.Account.LOGIN, registerAccountRequest.getLogin());
        account.put(Entity.Account.EMAIL, registerAccountRequest.getEmail());
        account.put(Entity.Account.PASSWORD_HASH, PasswordHashing.hash(registerAccountRequest.getPassword()));
        account.put(Entity.Account.IS_CONSENT_PERSONAL_DATA, registerAccountRequest.getConsentPersonalData());
        account.put(Entity.Account.USERNAME, registerAccountRequest.getUsername());
        account.put(Entity.Account.IS_PREMIUM, false);
        Storage.saveAccount(account);
    }

    /**
     * Delete all account`s data
     */

    public static void delete(DeleteAccountRequest deleteAccountRequest) {
        var login = JwsParsing.parseAuth(deleteAccountRequest.getAuthToken(), JWT_SUBJECT);
        Storage.deleteAccount(login);
    }

    /**
     * @return JWT
     */

    public static String authenticate(AuthenticateUserRequest authenticateUserRequest) throws AccountNotFound, AccessDenied {
        Map<String, Object> account = Storage.findAccountByLogin(authenticateUserRequest.getLogin())
                                             .orElseThrow(() -> new AccountNotFound(String.format(
                                                     "Account with login '%s' not found",
                                                     authenticateUserRequest.getLogin()
                                             )));

        String passwordHash = Collection.get(account, Entity.Account.PASSWORD_HASH);
        boolean invalidPasswordHash = !(PasswordHashing.verify(
                authenticateUserRequest.getPassword(),
                passwordHash
        ));
        if (invalidPasswordHash) {
            throw new AccessDenied(String.format(
                    "Invalid password for '%s' account",
                    authenticateUserRequest.getLogin()
            ));
        }

        String login = Collection.get(account, Entity.Account.LOGIN);
        Boolean premium = Collection.get(account, Entity.Account.IS_PREMIUM);
        return JwtIssuing.issueAuth(login, premium);
    }

    /**
     *
     * @param email of owner to send the code
     */

    public static void requestResetCode(String email) {
        Map<String, Object> account = Storage.findAccountByEmail(email)
                                             .orElseThrow(() -> new AccountNotFound(String.format(
                                                     "Account with email '%s' not found",
                                                     email
                                             )));
        String login = Collection.get(account, Entity.Account.LOGIN);
        var resetToken = JwtIssuing.issueReset(login);

        NotificationSending.send(resetCodeCommand(login, email), fillResetCodeTemplate(login, resetToken));
    }


    public static void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        var login = JwsParsing.parseReset(updatePasswordRequest.getResetToken(), JWT_SUBJECT);
        Storage.updateAccountPasswordHash(
                PasswordHashing.hash(updatePasswordRequest.getNewPassword()),
                login
        );
    }


    public static void updateEmail(UpdateEmailRequest updateEmailRequest) throws NoPersonalDataConsent {
        var login = JwsParsing.parseAuth(updateEmailRequest.getAuthToken(), JWT_SUBJECT);
        Storage.findAccountByEmail(updateEmailRequest.getEmail())
               .ifPresent(_ -> {
                   throw new DataNotUnique("Email '%s' is not unique!".formatted(updateEmailRequest.getEmail()));
               });
        Storage.findAccountByLogin(login)
               .ifPresent(account -> {
                   Boolean isConsentPersonalData = Collection.get(account, Entity.Account.IS_CONSENT_PERSONAL_DATA);
                   if (!isConsentPersonalData) {
                       throw new NoPersonalDataConsent();
                   }
                   Storage.updateAccountEmail(login, updateEmailRequest.getEmail());
               });
    }


    @SneakyThrows
    public static void updateAvatar(UpdateAvatarRequest updateAvatarRequest) {
        var login = JwsParsing.parseAuth(updateAvatarRequest.getAuthToken(), JWT_SUBJECT);
        String avatarPath = updateAvatarRequest.getAvatar()
                                               .uploadedFileName();

        byte[] avatar = Files.readAllBytes(Path.of(avatarPath));
        String bucket = Config.get("s3.buckets.images");
        String avatarUrl = S3.upload(
                bucket,
                S3.Key.uuid(avatar),
                avatar
        );
        Storage.updateAccountAvatarUrl(login, avatarUrl);
    }


    public static void updateContacts(UpdateContactsRequest updateContactsRequest) {
        var login = JwsParsing.parseAuth(updateContactsRequest.getAuthToken(), JWT_SUBJECT);
        String contacts = updateContactsRequest.getContacts()
                                               .getUrLs()
                                               .toString();
        Storage.updateAccountContactInfo(login, contacts);
    }


    public static void updateInterests(UpdateInterestsRequest request) {
        var login = JwsParsing.parseAuth(request.getAuthToken(), JWT_SUBJECT);
        Storage.updateAccountInterests(login, request.getInterests());
    }


    public static void updatePersonalDataConsent(UpdatePersonalDataConsentRequest updatePersonalDataConsentRequest) {
        var login = JwsParsing.parseAuth(updatePersonalDataConsentRequest.getAuthToken(), JWT_SUBJECT);
        boolean consent = updatePersonalDataConsentRequest.getConsentPersonalData();

        Storage.updateAccountPersonalDataConsent(login, consent);
        if (!consent) {
            Storage.updateAccountEmail(login, null);
        }
    }

    /**
     * Выдать премиум-статус
     */

    public static void upgrade(String login) throws AccountNotFound, AccountAlreadyUpgraded {
        Map<String, Object> account = Storage.findAccountByLogin(login)
                                             .orElseThrow(() -> new AccountNotFound(String.format(
                                                     "Account with login '%s' not found",
                                                     login
                                             )));
        Storage.updateAccountPremium(login, true);
        Scheduling.downgradePremiumAccount(
                login,
                Instant.now(Clock.systemUTC())
                       .plus(1, ChronoUnit.MONTHS)
        );
        String email = Collection.getOrNull(account, Entity.Account.EMAIL);
        NotificationSending.send(accountUpgradedCommand(login, email), fillAccountUpgradedTemplate(login));
    }

    /**
     * Убрать премиум-статус у аккаунта, чей срок подписки истек.
     */

    public static void downgrade(String login) {
        Storage.findAccountByLogin(login)
               .ifPresent(account -> {
                   String accountLogin = Collection.get(account, Entity.Account.LOGIN);
                   String email = Collection.getOrNull(account, Entity.Account.EMAIL);
                   Storage.updateAccountPremium(accountLogin, false);
                   NotificationSending.send(accountDowngradedCommand(accountLogin, email), fillAccountDowngradedTemplate(accountLogin));
               });
    }


    public static ProfilePublicView viewPublicProfile(String login) {
        var accountOptional = Storage.findAccountByLogin(login);
        String avatarUrl = accountOptional.map(account -> Collection.<String>getOrNull(account, Entity.Account.AVATAR_URL))
                                          .orElse(null);
        String username = accountOptional.map(account -> requireNonNullElseGet(
                                                 Collection.<String>getOrNull(account, Entity.Account.USERNAME),
                                                 () -> Collection.<String>get(account, Entity.Account.LOGIN)))
                                         .orElse(null);
        String contactInfo = accountOptional.map(account -> Collection.<String>getOrNull(account, Entity.Account.CONTACT_INFO))
                                            .orElse(null);
        List<String> interests = accountOptional.map(account -> Collection.<List<String>>getOrNull(account, Entity.Account.INTERESTS))
                                                .orElse(emptyList());

        return new ProfilePublicView(username, avatarUrl, contactInfo, interests);
    }


    public static ProfilePrivateView viewPrivateProfile(ViewPrivateProfile request) throws AccessDenied, AccountNotFound {
        var actualLogin = JwsParsing.parseAuth(request.getAuthToken(), JWT_SUBJECT);
        var login = request.getLogin();

        if (!Objects.equals(login, actualLogin)) {
            throw new AccessDenied("The Profile is not yours.");
        }
        return Storage.findAccountByLogin(login)
                      .map(account -> Jackson.convertToClass(account, ProfilePrivateView.class))
                      .orElseThrow(AccountNotFound::new); // маловероятно, пусть будет для информативности
    }

    /**
     * Notify all interested users that the vote just started
     */
    public static void notifyVoteStarted(String voteName, String skillName) {
        Storage.findAccountsInterestedIn(skillName)
               .forEach(account -> {
                   String login = Collection.get(account, Entity.Account.LOGIN);
                   String email = Collection.getOrNull(account, Entity.Account.EMAIL);
                   NotificationSending.send(NotificationSending.voteStartedCommand(login, email), fillVoteStartedTemplate(voteName));
               });
    }
}
