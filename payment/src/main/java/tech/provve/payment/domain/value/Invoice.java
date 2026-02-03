package tech.provve.payment.domain.value;

/**
 *
 * @param accountLogin аккаунт, на который был создан платёж
 * @param signature
 */
public record Invoice(String accountLogin, String signature) {

}
