    -- changeset timin: 2
CREATE TABLE users
(
    id           BIGSERIAL PRIMARY KEY,
    login        VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE DEFAULT now()
);
    -- rollback drop table users;
