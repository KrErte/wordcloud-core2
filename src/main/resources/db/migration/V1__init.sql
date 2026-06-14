CREATE TABLE text_job (
    id         UUID         NOT NULL PRIMARY KEY,
    status     VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    filename   VARCHAR(255),
    created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE word_count (
    id     BIGSERIAL    NOT NULL PRIMARY KEY,
    job_id UUID         NOT NULL REFERENCES text_job(id),
    word   VARCHAR(255) NOT NULL,
    count  INTEGER      NOT NULL,
    CONSTRAINT uq_word_count_job_word UNIQUE (job_id, word)
);

CREATE INDEX idx_word_count_job_id ON word_count (job_id);
