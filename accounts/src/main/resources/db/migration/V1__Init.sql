CREATE SCHEMA IF NOT EXISTS accounts;

CREATE TABLE accounts.accounts (
    login VARCHAR(50) PRIMARY KEY,
    email VARCHAR(254), -- RFC 5321
    avatar_url TEXT,
    premium BOOLEAN DEFAULT FALSE,
    password_hash TEXT NOT NULL,
    consent_personal_data BOOLEAN DEFAULT FALSE,
    username VARCHAR(30) NOT NULL
);

CREATE INDEX idx_accounts_email
ON accounts.accounts USING BTREE(email);

COMMENT ON TABLE accounts.accounts IS 'Таблица для хранения учетных записей пользователей';
COMMENT ON COLUMN accounts.accounts.login IS 'Логин пользователя';
COMMENT ON COLUMN accounts.accounts.email IS 'Email пользователя (может быть NULL, если не получено согласие)';
COMMENT ON COLUMN accounts.accounts.avatar_url IS 'Ссылка на аватар пользователя';
COMMENT ON COLUMN accounts.accounts.premium IS 'Является ли пользователь платным';
COMMENT ON COLUMN accounts.accounts.password_hash IS 'Хэшированный пароль пользователя';
COMMENT ON COLUMN accounts.accounts.consent_personal_data IS 'Флаг согласия пользователя на обработку персональных данных';
COMMENT ON COLUMN accounts.accounts.username IS 'Отображаемое имя пользователя';


CREATE TABLE accounts.premium_expiration (
    account_login VARCHAR(50) PRIMARY KEY REFERENCES accounts.accounts(login) ON DELETE CASCADE,
    expiry TIMESTAMP WITH TIME ZONE NOT NULL
);

COMMENT ON TABLE accounts.premium_expiration IS 'Таблица для хранения сроков окончания премиум-подписок пользователей.';
COMMENT ON COLUMN accounts.premium_expiration.account_login IS 'Логин аккаунта';
COMMENT ON COLUMN accounts.premium_expiration.expiry IS 'Срок окончания премиум-подписки';


CREATE OR REPLACE FUNCTION accounts.expire_premium_accounts()
RETURNS TABLE(
    login VARCHAR(50),
    email VARCHAR(254), -- RFC 5321
    avatar_url TEXT,
    premium BOOLEAN,
    password_hash VARCHAR(255),
    consent_personal_data BOOLEAN,
    username VARCHAR(30)
)
AS $$
BEGIN
    RETURN QUERY
    WITH expired_accounts AS (
        SELECT account_login
        FROM accounts.premium_expiration
        WHERE expiry <= NOW() AT TIME ZONE 'UTC'
    )
    UPDATE accounts.accounts acc
    SET premium = FALSE
    WHERE acc.login IN (SELECT account_login FROM expired_accounts)
    RETURNING acc.login, acc.email, acc.avatar_url, acc.premium, acc.password_hash, acc.consent_personal_data, acc.username;
END;
$$ LANGUAGE plpgsql VOLATILE SECURITY DEFINER;

COMMENT ON FUNCTION accounts.expire_premium_accounts() IS 'Функция для отключения премиум-статуса пользователям, срок подписки которых истек. Возвращает список обработанных аккаунтов.';