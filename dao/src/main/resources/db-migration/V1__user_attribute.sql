CREATE TABLE user_attribute
(
    id                    BIGSERIAL PRIMARY KEY,
    user_id               CHARACTER VARYING NOT NULL UNIQUE,
    user_role             CHARACTER VARYING NOT NULL,
    user_name             CHARACTER VARYING NOT NULL,
    phone_number          CHARACTER VARYING NOT NULL,
    created_at            TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX user_attribute__phone_number ON user_attribute(phone_number);

CREATE INDEX user_attribute__user_role ON user_attribute(user_role);
