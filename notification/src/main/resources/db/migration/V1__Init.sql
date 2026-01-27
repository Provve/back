CREATE SCHEMA IF NOT EXISTS notifications;

CREATE TABLE notifications.notifications (
    id SERIAL PRIMARY KEY,
    notified_account VARCHAR(50) REFERENCES accounts.accounts(login) ON DELETE CASCADE,
    level SMALLINT CHECK(level >= 0 AND level <= 2),
    message TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN notifications.notifications.id IS 'Идентификатор уведомления';
COMMENT ON COLUMN notifications.notifications.notified_account IS 'Аккаунт, для которого уведомление назначено';
COMMENT ON COLUMN notifications.notifications.level IS 'Уровень важности уведомления: 0 - error, 1 - warning, 2 - info';
COMMENT ON COLUMN notifications.notifications.message IS 'Содержание уведомления, HTML';
COMMENT ON COLUMN notifications.notifications.created_at IS 'Время создания уведомления';