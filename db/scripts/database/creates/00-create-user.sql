CREATE USER moniman PASSWORD 'monimanpass';
CREATE DATABASE moniman;
\c moniman
CREATE SCHEMA moniman AUTHORIZATION moniman;
REVOKE ALL ON DATABASE moniman FROM public;

GRANT CONNECT ON DATABASE moniman TO moniman;
GRANT CREATE ON DATABASE moniman TO moniman;
ALTER USER moniman SET SEARCH_PATH TO moniman;
