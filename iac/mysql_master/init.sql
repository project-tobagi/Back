-- MySQL Master Initialization Script

-- Create replication user
CREATE USER 'repl'@'%' IDENTIFIED WITH mysql_native_password BY 'replpassword';
GRANT REPLICATION SLAVE ON *.* TO 'repl'@'%';
FLUSH PRIVILEGES;

-- Ensure the binary logging is enabled
SHOW VARIABLES LIKE 'log_bin';

-- Display master status for replication setup
SHOW MASTER STATUS;
