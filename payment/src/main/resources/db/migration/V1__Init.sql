CREATE SCHEMA IF NOT EXISTS payment;

CREATE TABLE payment.invoices_robokassa (
    account_login VARCHAR(50) PRIMARY KEY REFERENCES accounts.accounts(login),
    signature TEXT NOT NULL
);

COMMENT ON TABLE payment.invoices_robokassa IS 'Таблица хранит информацию о платежах Robokassa, привязанных к аккаунтам пользователей.';
COMMENT ON COLUMN payment.invoices_robokassa.account_login IS 'Логин аккаунта, на который создан платёж.';
COMMENT ON COLUMN payment.invoices_robokassa.signature IS 'Подпись платежа в формате JWT. Используется для проверки подлинности платежей Robokassa.';