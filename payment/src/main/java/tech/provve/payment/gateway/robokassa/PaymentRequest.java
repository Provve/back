package tech.provve.payment.gateway.robokassa;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

/**
 * Запрос на создание платежной ссылки
 *
 * @param merchantLogin id магазина
 * @param outSum        сумма оплаты
 */
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public record PaymentRequest(String merchantLogin, Integer outSum, InvoiceType invoiceType,
                             Map<String, String> userFields
) {

    public static final String ACCOUNT_FIELD = "account";

}
