CREATE TABLE rs24.rs24schem.users
(
    id       bigserial primary key,
    username varchar(36) not null,
    password varchar(80) not null,
    email    varchar(50) unique
);

CREATE TABLE rs24.rs24schem.roles
(
    id   bigserial primary key,
    name varchar(50) not null
);

CREATE TABLE rs24.rs24schem.users_roles
(
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id)
);