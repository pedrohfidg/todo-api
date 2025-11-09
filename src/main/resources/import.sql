-- ROLES
INSERT INTO roles (authority) VALUES ('ROLE_ADMIN');
INSERT INTO roles (authority) VALUES ('ROLE_USER');

-- USERS (senhas já encriptadas)
INSERT INTO users (nome, email, password, criado_em, atualizado_em) VALUES ('Admin', 'admin@devex.com', '$2b$12$RcFcYAQd0WCSrTLq3m8cxOhXYS3/cl6JPTiv2UOW4pSO0SsHYHofu', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC');
INSERT INTO users (nome, email, password, criado_em, atualizado_em) VALUES ('Pedro', 'pedro@devex.com', '$2b$12$79KDHO/WnH95VLooOPZR.uA.9pp4il/Vb5vgH1wT/baomm/wriXkm', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC');
INSERT INTO users (nome, email, password, criado_em, atualizado_em) VALUES ('João', 'joao@devex.com', '$2b$12$kxDwOJTjCxX3KTsLLXOPQebBhQUhpNEaa1/KLtcAtF3v5py/1uLm6', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC');

-- USERS_ROLES (associação muitos-para-muitos)
-- Admin com ROLE_ADMIN e ROLE_USER
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (1, 2);

-- Pedro com ROLE_USER
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);

-- João com ROLE_USER
INSERT INTO users_roles (user_id, role_id) VALUES (3, 2);


INSERT INTO tasks (titulo, descricao, status, data_criacao, data_conclusao, user_id) VALUES ('Estudar Spring', 'Aprofundar conceitos de segurança e validações', 'PENDENTE', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', NULL, 1);

INSERT INTO tasks (titulo, descricao, status, data_criacao, data_conclusao, user_id) VALUES ('Fazer compras', 'Comprar frutas e legumes', 'CONCLUIDA', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 1);

INSERT INTO tasks (titulo, descricao, status, data_criacao, data_conclusao, user_id) VALUES ('Treinar', 'Treino de perna na academia', 'PENDENTE', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', NULL, 2);

INSERT INTO tasks (titulo, descricao, status, data_criacao, data_conclusao, user_id) VALUES ('Planejar projeto pessoal', 'Definir roadmap do projeto full stack', 'CONCLUIDA', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 2);

-- NOVOS SEEDS

-- Admin
INSERT INTO tasks (titulo, descricao, status, data_criacao, data_conclusao, user_id) VALUES ('Revisar servidor', 'Verificar logs e ajustar configurações no EC2', 'PENDENTE', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', NULL, 1);
INSERT INTO tasks (titulo, descricao, status, data_criacao, data_conclusao, user_id) VALUES ('Atualizar relatórios', 'Atualizar dashboards no Power BI', 'CONCLUIDA', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 1);

-- Pedro
INSERT INTO tasks (titulo, descricao, status, data_criacao, data_conclusao, user_id) VALUES ('Estudar React', 'Componentes, hooks e context API', 'PENDENTE', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', NULL, 2);
INSERT INTO tasks (titulo, descricao, status, data_criacao, data_conclusao, user_id) VALUES ('Refatorar projeto de automação', 'Melhorias de logs e tratamento de erros', 'PENDENTE', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', NULL, 2);
INSERT INTO tasks (titulo, descricao, status, data_criacao, data_conclusao, user_id) VALUES ('Backup de dados', 'Realizar backup da base para o S3', 'CONCLUIDA', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 2);

-- João
INSERT INTO tasks (titulo, descricao, status, data_criacao, data_conclusao, user_id) VALUES ('Aprender Docker', 'Conceitos básicos de containers', 'PENDENTE', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', NULL, 3);
INSERT INTO tasks (titulo, descricao, status, data_criacao, data_conclusao, user_id) VALUES ('Limpeza de email', 'Organizar caixa de entrada', 'CONCLUIDA', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', 3);
INSERT INTO tasks (titulo, descricao, status, data_criacao, data_conclusao, user_id) VALUES ('Organizar agenda', 'Definir tarefas da semana', 'PENDENTE', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', NULL, 3);
