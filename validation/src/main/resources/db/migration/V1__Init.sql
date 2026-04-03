CREATE SCHEMA IF NOT EXISTS validation;

CREATE TABLE IF NOT EXISTS validation.observation (
    examinee VARCHAR(50) REFERENCES accounts.accounts(login),
    violations TEXT NOT NULL,
    cheated BOOLEAN
);
COMMENT ON TABLE validation.observation IS
    'Хранит результат наблюдения Антифрода за экзаменуемым во время прохождения экзамена.';

COMMENT ON COLUMN validation.observation.examinee IS
    'За кем велось наблюдение.';

COMMENT ON COLUMN validation.observation.violations IS
    'Что нарушено';

COMMENT ON COLUMN validation.observation.cheated IS
    'Читерил ли экзаменуемый.';
