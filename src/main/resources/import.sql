INSERT INTO tb_user (name, email, password) VALUES ('Maria Brown', 'maria@gmail.com', '$2a$10$4CRVa7fp3WUIVF2zSA3OYe/HO044Vx3.UnltkJxdSzuKm/rQGX2gC');
INSERT INTO tb_user (name, email, password) VALUES ('Alex Green', 'alex@gmail.com', '$2a$10$4CRVa7fp3WUIVF2zSA3OYe/HO044Vx3.UnltkJxdSzuKm/rQGX2gC');
INSERT INTO tb_user (name, email, password) VALUES ('Luan Victor', 'luan@gmail.com', '$2a$10$4CRVa7fp3WUIVF2zSA3OYe/HO044Vx3.UnltkJxdSzuKm/rQGX2gC');

INSERT INTO tb_role (authority) VALUES ('ROLE_MEMBER');
INSERT INTO tb_role (authority) VALUES ('ROLE_RESPONSIBLE');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMINISTRATOR');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 3);