INSERT INTO rs24.rs24schem.roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO rs24.rs24schem.users (username, password, email)
VALUES ('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
       ('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com');

INSERT INTO rs24.rs24schem.users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2);