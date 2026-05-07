package tech.provve.accounts.domain.model;

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
        Boolean isPremium
) {

}
