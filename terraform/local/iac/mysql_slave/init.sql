---- MySQL Slave Initialization Script
--
---- Configure replication with the Master
--CHANGE MASTER TO
--  MASTER_HOST='mysql-master',
--  MASTER_USER='repl',
--  MASTER_PASSWORD='replpassword';
--
---- Start the replication process
--START SLAVE;
