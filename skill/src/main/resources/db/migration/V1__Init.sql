CREATE SCHEMA IF NOT EXISTS skill;

CREATE DOMAIN skill.TAG AS VARCHAR(20);
COMMENT ON DOMAIN skill.TAG IS 'Поисковой тег';


CREATE TABLE skill.vote (
    name VARCHAR(100) PRIMARY KEY,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    success BOOLEAN,
    author VARCHAR(50) REFERENCES accounts.accounts(login),
    deadline TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    arguments TEXT,
    type SMALLINT CHECK(type >= 0 AND type <= 2),
    tags skill.TAG[]
);
COMMENT ON TABLE skill.vote IS 'Общая форма голосования';
COMMENT ON COLUMN skill.vote.name IS 'Название голосования. Он же и id объекта голосования';
COMMENT ON COLUMN skill.vote.active IS 'Признак активного голосования';
COMMENT ON COLUMN skill.vote.success IS 'Итог успешного голосования';
COMMENT ON COLUMN skill.vote.author IS 'Автор голосования';
COMMENT ON COLUMN skill.vote.deadline IS 'Конечный срок, когда голосование закроется, будет подсчитан результат и совершенно действие.';
COMMENT ON COLUMN skill.vote.arguments IS 'Аргументы за совершение действия, предложенного в голосовании.';
COMMENT ON COLUMN skill.vote.type IS '0 = добавление навыка, 1 = удаление навыка, 2 = добавление экзамена';

CREATE INDEX idx_vote_tags ON skill.vote USING GIN(tags);



CREATE TABLE skill.skill (
    name VARCHAR(100) PRIMARY KEY,
    tags skill.TAG[]
);
COMMENT ON TABLE skill.skill IS 'Таблица навыков';
COMMENT ON COLUMN skill.skill.name IS 'Название навыка';

CREATE INDEX idx_skill_tags ON skill.skill USING GIN(tags);



CREATE TABLE skill.exam_add_vote (
    vote_name VARCHAR(100) REFERENCES skill.vote(name) ON DELETE CASCADE, -- удалить при удалении самого голосования (модерацией)
    skill_name VARCHAR(100) REFERENCES skill.skill(name) ON DELETE CASCADE, -- удалить при удалении навыка
    description TEXT,
    material_url TEXT NOT NULL
);
COMMENT ON TABLE skill.exam_add_vote IS 'Данные голосования на добавление экзамена (type = 2)';
COMMENT ON COLUMN skill.exam_add_vote.skill_name IS 'Связанный навык';
COMMENT ON COLUMN skill.exam_add_vote.description IS 'Финальная постановка задания для экзаменуемых';
COMMENT ON COLUMN skill.exam_add_vote.material_url IS 'Ссылка на учебный материал в S3';

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



CREATE TABLE skill.reactions (
    voter VARCHAR(50) REFERENCES accounts.accounts(login),
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