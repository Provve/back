CREATE SCHEMA IF NOT EXISTS statemachine;

CREATE TYPE statemachine.save_exam_state AS ENUM ('UNPREPARED', 'PREPARED');

CREATE TABLE statemachine.save_exam (
    name TEXT PRIMARY KEY,
    state statemachine.save_exam_state NOT NULL,
    author VARCHAR(50) REFERENCES accounts.accounts(login) ON DELETE CASCADE,
    delayed_vote_json TEXT NOT NULL
);
COMMENT ON COLUMN statemachine.save_exam.delayed_vote_json IS 'Параметры голосования для отложеного создания';


CREATE TYPE statemachine.check_solution_state AS ENUM ('UNPREPARED', 'PREPARED', 'RUNNING', 'STOPPED');

CREATE TABLE statemachine.check_solution (
        name TEXT PRIMARY KEY,
        state statemachine.check_solution_state NOT NULL,
        examinee VARCHAR(50) REFERENCES accounts.accounts(login) ON DELETE CASCADE
);
COMMENT ON COLUMN statemachine.check_solution.examinee IS 'Автор решения';