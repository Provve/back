package tech.provve.accounts.domain.model;

public record Account(
        String login,
        String email,
        String passwordHash,
        Boolean isConsentPersonalData,
        String username,
        String avatarUrl,
        Boolean isPremium
) {

}
