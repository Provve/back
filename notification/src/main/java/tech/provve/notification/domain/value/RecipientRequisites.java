package tech.provve.notification.domain.value;

/**
 * Данные получателя
 *
 * @param login of the account to whom send
 * @param email target
 */
public record RecipientRequisites(String login, String email) {

}
