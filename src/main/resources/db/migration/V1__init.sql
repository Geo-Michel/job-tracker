CREATE TABLE users (
    id            BIGSERIAL PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE job_applications (
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    company      VARCHAR(150) NOT NULL,
    position     VARCHAR(150) NOT NULL,
    status       VARCHAR(20)  NOT NULL,
    location     VARCHAR(150),
    job_url      VARCHAR(500),
    applied_date DATE,
    notes        TEXT,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE INDEX idx_job_applications_user_status ON job_applications (user_id, status);
