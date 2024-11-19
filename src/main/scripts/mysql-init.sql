DROP DATABASE IF EXISTS restdb;
DROP USER IF EXISTS `restadmin`@`%`;
CREATE DATABASE IF NOT EXISTS restdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- Use mysql_native_password as default authentication plugin running the container with command `mysqld --default-authentication-plugin=mysql_native_password`
CREATE USER IF NOT EXISTS `restadmin`@`%` IDENTIFIED WITH caching_sha2_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `restdb`.* TO `restadmin`@`%`;