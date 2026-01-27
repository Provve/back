CREATE SCHEMA IF NOT EXISTS accounts;

CREATE TABLE accounts.accounts (
    login VARCHAR(50) PRIMARY KEY,
    email VARCHAR(254),
    avatar_url TEXT,
    premium BOOLEAN DEFAULT FALSE,
    password_hash VARCHAR(255) NOT NULL,
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