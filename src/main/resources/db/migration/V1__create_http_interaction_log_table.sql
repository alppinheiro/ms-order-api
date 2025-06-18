CREATE TABLE http_interaction_log (
    id UUID PRIMARY KEY,
    timestamp TIMESTAMP NOT NULL,
    direction VARCHAR(10) NOT NULL,
    url TEXT NOT NULL,
    method VARCHAR(10) NOT NULL,
    headers TEXT,
    body TEXT,
    status_code INT,
    response_body TEXT,
    duration_ms BIGINT
);