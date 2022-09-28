CREATE TABLE IF NOT EXISTS rs24.rs24schem.products
(
    id          bigserial primary key,
    name        varchar,
    description text,
    price       integer,
    image       varchar,
    category    bigint references rs24.rs24schem.categories (id) not null,
    date_added  bigint,
    status      boolean
);