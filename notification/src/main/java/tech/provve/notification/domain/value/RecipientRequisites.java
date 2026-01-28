package tech.provve.notification.domain.value;

import org.jetbrains.annotations.Nullable;

/**
 * Данные получателя
 *
 * @param login of the account to whom send
 * @param email target
 */
public record RecipientRequisites(String login, @Nullable String email) {

}
