CREATE TABLE user_attribute
(
    id                    BIGSERIAL PRIMARY KEY,
    user_id               CHARACTER VARYING NOT NULL,
    user_role             CHARACTER VARYING NOT NULL,
    created_at            TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
