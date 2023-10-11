CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    email varchar(255),
    name varchar(255),
    password varchar(255)
);

CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name varchar(255)
);

CREATE TABLE IF NOT EXISTS users_roles (
    user_id BIGINT NOT NULL REFERENCES users(id),
    role_id BIGINT NOT NULL REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS links(
    id BIGSERIAL NOT NULL NOT NULL PRIMARY KEY,
    short_link varchar(255),
    source_link varchar(255),
    user_id BIGINT NOT NULL REFERENCES users(id),
    site varchar(255)
);

CREATE TABLE IF NOT EXISTS link_clicks(
    id BIGSERIAL NOT NULL NOT NULL PRIMARY KEY,
    click_timestamp TIMESTAMP WITHOUT TIME ZONE,
    link_id BIGINT NOT NULL REFERENCES links(id)
);
