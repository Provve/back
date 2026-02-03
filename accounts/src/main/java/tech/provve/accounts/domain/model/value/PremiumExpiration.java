package tech.provve.accounts.domain.model.value;

import java.time.OffsetDateTime;

/**
 * Срок окончания премиум-подписки пользователея.
 *
 * @param login  аккаунта
 * @param expiry Срок окончания премиум-подписки
 */
public record PremiumExpiration(String login, OffsetDateTime expiry) {

}
