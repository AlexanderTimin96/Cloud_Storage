-- changeset timin: 4
INSERT INTO users (login, password)
VALUES ('user@test.ru', '$2a$12$BG27d4Oc.rez5khl5U2WGek22o98QKVM.jZilHAJqx8rGEzF4vyii'),
       ('admin@test.ru', '2a$12$Q1P3iwZTnzZqkXf/pJvUJuwhsNqniruPnM1kHxMvai25vIULzDZFO');

INSERT INTO user_roles(user_id, roles)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN')