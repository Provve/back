package tech.provve.payment.gateway.robokassa;

/**
 * Тип ссылки
 */
public enum InvoiceType {

    /**
     * Одноразовая
     */
    OneTime,

    /**
     * Многоразовая
     */
    Reusable;

    @Override
    public String toString() {
        return name();
    }
}
