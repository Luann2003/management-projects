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

INSERT INTO tb_project (name, description, start_date, finish_date) VALUES ('test 1', 'test descrição 1', TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z', TIMESTAMP WITH TIME ZONE '2020-07-15T14:33:25.12345Z' )
INSERT INTO tb_project (name, description, start_date, finish_date) VALUES ('test 2', 'test descrição 2', TIMESTAMP WITH TIME ZONE '2020-08-13T22:33:07.12345Z', TIMESTAMP WITH TIME ZONE '2020-07-15T16:15:22.12345Z' )
INSERT INTO tb_project (name, description, start_date, finish_date) VALUES ('test 3', 'test descrição 3', TIMESTAMP WITH TIME ZONE '2020-08-22T15:20:07.12345Z', TIMESTAMP WITH TIME ZONE '2020-07-15T12:35:35.12345Z' )
INSERT INTO tb_project (name, description, start_date, finish_date) VALUES ('test 4', 'test descrição 4', TIMESTAMP WITH TIME ZONE '2020-09-13T10:50:07.12345Z', TIMESTAMP WITH TIME ZONE '2020-09-15T11:36:55.12345Z' )

INSERT INTO tb_task (project_id, name, description, start_date, finish_date) VALUES (1, 'task 1', 'descrição da tarefa do projeto 1 ', TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z', TIMESTAMP WITH TIME ZONE '2020-07-15T14:33:25.12345Z' )
INSERT INTO tb_task (project_id, name, description, start_date, finish_date) VALUES (2, 'task 2', ' descrição da tarefa do projeto 2', TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z', TIMESTAMP WITH TIME ZONE '2020-07-15T14:33:25.12345Z' )
INSERT INTO tb_task (project_id, name, description, start_date, finish_date) VALUES (2, 'task 3', 'descrição da tarefa do projeto 2 ', TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z', TIMESTAMP WITH TIME ZONE '2020-07-15T14:33:25.12345Z' )
INSERT INTO tb_task (project_id, name, description, start_date, finish_date) VALUES (3, 'task 4', 'descrição da tarefa do projeto 3 ', TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z', TIMESTAMP WITH TIME ZONE '2020-07-15T14:33:25.12345Z' )
INSERT INTO tb_task (project_id, name, description, start_date, finish_date) VALUES (4, 'task 5', ' descrição da tarefa do projeto 4', TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z', TIMESTAMP WITH TIME ZONE '2020-07-15T14:33:25.12345Z' )
INSERT INTO tb_task (project_id, name, description, start_date, finish_date) VALUES (4, 'task 6', 'descrição da tarefa do projeto 4 ', TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z', TIMESTAMP WITH TIME ZONE '2020-07-15T14:33:25.12345Z' )









