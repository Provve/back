CREATE SCHEMA IF NOT EXISTS skill;

CREATE TABLE skill.vote (
    name VARCHAR(100) PRIMARY KEY,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    success BOOLEAN,
    author VARCHAR(50) REFERENCES accounts.accounts(login),
    deadline TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    arguments TEXT,
    type SMALLINT CHECK(type >= 0 AND type <= 2),
    tags TEXT[]
);
COMMENT ON TABLE skill.vote IS 'Общая форма голосования';
COMMENT ON COLUMN skill.vote.name IS 'Название голосования. Он же и id объекта голосования';
COMMENT ON COLUMN skill.vote.active IS 'Признак активного голосования';
COMMENT ON COLUMN skill.vote.success IS 'Итог успешного голосования';
COMMENT ON COLUMN skill.vote.author IS 'Автор голосования';
COMMENT ON COLUMN skill.vote.deadline IS 'Конечный срок, когда голосование закроется, будет подсчитан результат и совершенно действие.';
COMMENT ON COLUMN skill.vote.arguments IS 'Аргументы за совершение действия, предложенного в голосовании.';
COMMENT ON COLUMN skill.vote.type IS '0 = добавление навыка, 1 = удаление навыка, 2 = добавление экзамена';
COMMENT ON COLUMN skill.vote.tags IS 'Поисковые теги';

CREATE INDEX idx_vote_tags ON skill.vote USING GIN(tags);



CREATE TABLE skill.ts_vote_ru (
    vote_name VARCHAR(100) REFERENCES skill.vote(name) ON DELETE CASCADE,
    arguments TSVECTOR NOT NULL
);
COMMENT ON TABLE skill.ts_vote_ru IS 'Хранит подготовленные для поиска значения vote в русской локали';
COMMENT ON COLUMN skill.ts_vote_ru.arguments IS 'Подготовленный для поиска vote.arguments';

CREATE INDEX ts_vote_ru_idx ON skill.ts_vote_ru USING GIN (arguments);

CREATE OR REPLACE FUNCTION INSERT_INTO_TS_VOTE() RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO skill.ts_vote_ru (vote_name, arguments)
    VALUES (NEW.name, to_tsvector('russian', NEW.arguments));
    RETURN NEW;
END;
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER AFTER_INSERT_VOTE
AFTER INSERT ON skill.vote FOR EACH ROW
EXECUTE FUNCTION INSERT_INTO_TS_VOTE();



CREATE TABLE skill.skill (
    name VARCHAR(100) PRIMARY KEY,
    tags TEXT[]
);
COMMENT ON TABLE skill.skill IS 'Таблица навыков';
COMMENT ON COLUMN skill.skill.name IS 'Название навыка';
COMMENT ON COLUMN skill.skill.tags IS 'Поисковые теги';

CREATE INDEX idx_skill_tags ON skill.skill USING GIN(tags);



CREATE TABLE skill.exam_add_vote (
    vote_name VARCHAR(100) REFERENCES skill.vote(name) ON DELETE CASCADE, -- удалить при удалении самого голосования (модерацией)
    skill_name VARCHAR(100) REFERENCES skill.skill(name) ON DELETE CASCADE, -- удалить при удалении навыка
    description VARCHAR(3000),
    private_archive_url TEXT NOT NULL,
    public_archive_url TEXT NOT NULL
);
COMMENT ON TABLE skill.exam_add_vote IS 'Данные голосования на добавление экзамена (type = 2)';
COMMENT ON COLUMN skill.exam_add_vote.skill_name IS 'Связанный навык';
COMMENT ON COLUMN skill.exam_add_vote.description IS 'Финальная постановка задания для экзаменуемых';
COMMENT ON COLUMN skill.exam_add_vote.private_archive_url IS 'Проверяющая часть экзамена';
COMMENT ON COLUMN skill.exam_add_vote.public_archive_url IS 'Проверяемая часть экзамена, задание';

CREATE OR REPLACE FUNCTION delete_related_vote()
RETURNS TRIGGER AS $$
BEGIN
    PERFORM * FROM skill.vote WHERE name = OLD.vote_name;

    IF FOUND THEN
        DELETE FROM skill.vote WHERE name = OLD.vote_name;
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;
COMMENT ON FUNCTION delete_related_vote() IS 'При удалении навыка, голосование на добавление к нему экзамена теряет смысл. Не вызывать вручную!';

CREATE TRIGGER before_delete_exam_add_vote
BEFORE DELETE ON skill.exam_add_vote
FOR EACH ROW EXECUTE PROCEDURE delete_related_vote();



CREATE TABLE skill.exam (
    name VARCHAR(100) PRIMARY KEY,
    skill_name VARCHAR(100) REFERENCES skill.skill(name) ON DELETE CASCADE,
    description VARCHAR(3000) NOT NULL,
    private_archive_url TEXT NOT NULL,
    public_archive_url TEXT NOT NULL
);
COMMENT ON TABLE skill.exam IS 'Данные экзамена.';
COMMENT ON COLUMN skill.exam.name IS 'Название экзамена';
COMMENT ON COLUMN skill.exam.skill_name IS 'Какой навык экзамен проверяет';
COMMENT ON COLUMN skill.exam.description IS 'Постановка задания для экзаменуемых';
COMMENT ON COLUMN skill.exam.private_archive_url IS 'Проверяющая часть экзамена';
COMMENT ON COLUMN skill.exam.public_archive_url IS 'Проверяемая часть экзамена, задание';



CREATE TABLE skill.ts_exam_ru (
    exam_name VARCHAR(100) REFERENCES skill.exam(name) ON DELETE CASCADE,
    description TSVECTOR NOT NULL
);
COMMENT ON TABLE skill.ts_exam_ru IS 'Хранит подготовленные для поиска значения exam в русской локали';
COMMENT ON COLUMN skill.ts_exam_ru.description IS 'Подготовленный для поиска exam.description';

CREATE INDEX ts_exam_ru_idx ON skill.ts_exam_ru USING GIN (description);

CREATE OR REPLACE FUNCTION INSERT_INTO_TS_EXAM() RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO skill.ts_exam_ru (exam_name, description)
    VALUES (NEW.name, to_tsvector('russian', NEW.description));
    RETURN NEW;
END;
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER AFTER_INSERT_EXAM
AFTER INSERT ON skill.exam FOR EACH ROW
EXECUTE FUNCTION INSERT_INTO_TS_EXAM();



CREATE TABLE skill.result (
     exam_name VARCHAR(100) REFERENCES skill.skill(name) ON DELETE CASCADE,
     examinee VARCHAR(50) REFERENCES accounts.accounts(login) ON DELETE CASCADE,
     duration INTERVAL NOT NULL
);



CREATE TABLE skill.reactions (
    voter VARCHAR(50) REFERENCES accounts.accounts(login) ON DELETE CASCADE,
    vote_name VARCHAR(100) REFERENCES skill.vote(name) ON DELETE CASCADE,
    reaction BIT(1) NOT NULL,

    UNIQUE (voter, vote_name)
);
COMMENT ON TABLE skill.reactions IS 'Таблица реакций участников на голосования';
COMMENT ON COLUMN skill.reactions.voter IS 'Голосующий участник';
COMMENT ON COLUMN skill.reactions.vote_name IS 'Связанный навык, за который идёт голосование';
COMMENT ON COLUMN skill.reactions.reaction IS '1 — "за", 0 — "против"';

CREATE FUNCTION skill.get_reactions_total(vote_name_param VARCHAR(100))
RETURNS TABLE(total_positive int, total_negative int)
AS $$
BEGIN
   RETURN QUERY
   SELECT COUNT(CASE WHEN reaction = '1' THEN 1 END)::INT AS total_positive,
          COUNT(CASE WHEN reaction = '0' THEN 1 END)::INT AS total_negative
   FROM skill.reactions
   WHERE vote_name = vote_name_param;
END;
$$ LANGUAGE plpgsql;



CREATE TABLE skill.comment (
    id SERIAL PRIMARY KEY,
    author VARCHAR(50) REFERENCES accounts.accounts(login),
    content VARCHAR(500) NOT NULL,
    created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    vote_name VARCHAR(100) REFERENCES skill.vote(name)
);
COMMENT ON TABLE skill.comment IS 'Комментарии к голосованиям';
COMMENT ON COLUMN skill.comment.id IS 'Идентификатор комментария';
COMMENT ON COLUMN skill.comment.author IS 'Автор комментария';
COMMENT ON COLUMN skill.comment.content IS 'Содержание комментария';
COMMENT ON COLUMN skill.comment.created IS 'Время создания комментария';
COMMENT ON COLUMN skill.comment.vote_name IS 'Связанное голосование';

CREATE INDEX idx_comment_vote_name ON skill.comment(vote_name);



CREATE TABLE skill.session (
    owner VARCHAR(50) REFERENCES accounts.accounts(login),
    exam_name VARCHAR(100) REFERENCES skill.exam(name),
    created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE skill.session IS 'Таблица для хранения сессий экзаменуемых';
COMMENT ON COLUMN skill.session.owner IS 'Логин экзаменуемого';
COMMENT ON COLUMN skill.session.exam_name IS 'Проводимый экзамен';
COMMENT ON COLUMN skill.session.created IS 'Время начала сессии';