package tech.provve.accounts.domain.model;

import java.util.UUID;

public record Account(
        UUID id,
        String login,
        String email,
        String password, // hashed passsword
        Boolean isConsentPersonalDataAccepted,
        String username,
        String avatarUrl,
        Boolean isPremium
) {

}
