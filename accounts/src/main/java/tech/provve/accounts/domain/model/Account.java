package tech.provve.accounts.domain.model;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * Аккаунт, он же профиль (в Provve аккаунт имеет ровно один профиль)
 */
public record Account(
        String login,
        String email,
        String passwordHash,
        Boolean isConsentPersonalData,
        String username,
        String avatarUrl,
        String contactInfo,
        List<String> interests,
        Boolean isPremium
) {

    public Account(String login,
                   String email,
                   String passwordHash,
                   Boolean isConsentPersonalData,
                   String username,
                   String avatarUrl,
                   String contactInfo,
                   Boolean isPremium) {
        this(login, email, passwordHash, isConsentPersonalData, username, avatarUrl, contactInfo, emptyList(), isPremium);
    }
}
