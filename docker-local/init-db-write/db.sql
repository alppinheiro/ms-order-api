-- Criação do banco principal
CREATE DATABASE order_db;

-- Criação de usuário de leitura (opcional: para replicação ou uso no slave)
CREATE USER order_user WITH ENCRYPTED PASSWORD 'order_user';

-- Permissões para leitura
GRANT CONNECT ON DATABASE order_db TO order_user;
GRANT USAGE ON SCHEMA public TO order_user;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO order_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT ON TABLES TO order_user;
