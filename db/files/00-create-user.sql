CREATE USER quotesc PASSWORD 'quotescpass';
CREATE DATABASE quotesc;
\c quotesc
CREATE SCHEMA quotesc AUTHORIZATION quotesc;
REVOKE ALL ON DATABASE quotesc FROM public;
GRANT CONNECT ON DATABASE quotesc TO quotesc;
GRANT CREATE ON DATABASE quotesc TO quotesc;
ALTER USER quotesc SET SEARCH_PATH TO quotesc;
