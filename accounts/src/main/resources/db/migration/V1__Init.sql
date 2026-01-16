CREATE SCHEMA IF NOT EXISTS accounts;

CREATE TABLE accounts.accounts (
    id UUID PRIMARY KEY,
    login VARCHAR NOT NULL,
    email VARCHAR,
    avatar_url TEXT,
    password_hash VARCHAR(255) NOT NULL,
    consent_personal_data BOOLEAN DEFAULT FALSE,
    username VARCHAR NOT NULL
);

COMMENT ON TABLE accounts.accounts IS 'Таблица для хранения учетных записей пользователей';
COMMENT ON COLUMN accounts.accounts.id IS 'Уникальный идентификатор пользователя';
COMMENT ON COLUMN accounts.accounts.login IS 'Логин пользователя';
COMMENT ON COLUMN accounts.accounts.email IS 'Email пользователя (может быть NULL, если не получено согласие)';
COMMENT ON COLUMN accounts.accounts.avatar_url IS 'Ссылка на аватар пользователя';
COMMENT ON COLUMN accounts.accounts.password_hash IS 'Хэшированный пароль пользователя';
COMMENT ON COLUMN accounts.accounts.consent_personal_data IS 'Флаг согласия пользователя на обработку персональных данных';
COMMENT ON COLUMN accounts.accounts.username IS 'Отображаемое имя пользователя';