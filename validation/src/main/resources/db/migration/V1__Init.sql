CREATE SCHEMA IF NOT EXISTS validation;

CREATE TABLE IF NOT EXISTS validation.observation (
    examinee VARCHAR(50) REFERENCES accounts.accounts(login),
    violations TEXT NOT NULL,
    cheated BOOLEAN
);
COMMENT ON TABLE validation.observation IS
    'Хранит результат наблюдения Антифрода за экзаменуемым во время прохождения экзамена.';
COMMENT ON COLUMN validation.observation.examinee IS 'За кем велось наблюдение.';
COMMENT ON COLUMN validation.observation.violations IS 'Что нарушено';
COMMENT ON COLUMN validation.observation.cheated IS 'Читерил ли экзаменуемый.';



CREATE TABLE IF NOT EXISTS validation.container (
    examinee VARCHAR(50) REFERENCES accounts.accounts(login),
    exam_name VARCHAR(100) NOT NULL, -- без FK, потому что невалидное значение не может прийти
    container_id VARCHAR(64) NOT NULL -- длина Container ID в Docker
);
COMMENT ON TABLE validation.container IS 'Хранит id контейнеров, в которых проверяется решение.';
COMMENT ON COLUMN validation.container.examinee IS 'Автор решения';
COMMENT ON COLUMN validation.container.container_id IS 'ID контейнера (как в docker ps)';
COMMENT ON COLUMN validation.container.exam_name IS 'По какому экзамену сделано решение';