CREATE TABLE user_credentials
(
    id                    BIGSERIAL PRIMARY KEY,
    user_id               BIGSERIAL NOT NULL,
    password              CHARACTER VARYING NOT NULL,
    created_at            TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES user_attribute(id)
);

CREATE INDEX user_id_index ON user_credentials(user_id);
