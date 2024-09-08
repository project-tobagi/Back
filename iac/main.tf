terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.0"
    }
  }
}

provider "docker" {}

# Define a Docker network
resource "docker_network" "custom_network" {
  name = "custom_network"
}

# MySQL Master
# resource "docker_container" "mysql_master" {
#   image = "mysql:8.0"
#   name  = "mysql-master"
#   env = [
#     "MYSQL_ROOT_PASSWORD=rootpassword",
#     "MYSQL_REPLICATION_USER=repl",
#     "MYSQL_REPLICATION_PASSWORD=replpassword",
#     "MYSQL_DATABASE=replication_test",
#   ]
#   ports {
#     internal = 3306
#     external = 3306
#   }
#   volumes {
#     host_path      = "/Users/kimsunjoo/Desktop/spring_study/hello-spring/iac/mysql_master/init.sql"
#     container_path = "/docker-entrypoint-initdb.d/init.sql"
#   }
#   volumes {
#     host_path      = "/Users/kimsunjoo/Desktop/spring_study/hello-spring/iac/mysql_master/my.cnf"
#     container_path = "/etc/mysql/my.cnf"
#   }
#   networks_advanced {
#     name = docker_network.custom_network.name
#   }
# }
#
# # MySQL Slave
# resource "docker_container" "mysql_slave" {
#   image = "mysql:8.0"
#   name  = "mysql-slave"
#   env = [
#     "MYSQL_ROOT_PASSWORD=rootpassword",
#     "MYSQL_REPLICATION_USER=repl",
#     "MYSQL_REPLICATION_PASSWORD=replpassword",
#   ]
#   depends_on = [docker_container.mysql_master]
#   volumes {
#     host_path      = "/Users/kimsunjoo/Desktop/spring_study/hello-spring/iac/mysql_slave/init.sql"
#     container_path = "/docker-entrypoint-initdb.d/init.sql"
#   }
#   volumes {
#     host_path      = "/Users/kimsunjoo/Desktop/spring_study/hello-spring/iac/mysql_slave/my.cnf"
#     container_path = "/etc/mysql/my.cnf"
#   }
#   networks_advanced {
#     name = docker_network.custom_network.name
#   }
# }

# PostgreSQL Master
resource "docker_container" "postgres_master" {
  image = "postgis/postgis:13-3.1"
  name  = "postgres-master"
  env = [
    "POSTGRES_PASSWORD=rootpassword",
    "POSTGRES_USER=postgres",
    "POSTGRES_PASSWORD=postgres",
    "POSTGRES_DB=replication_test",
    "PGDATA=/var/lib/postgresql/data/pgdata",
    "POSTGRES_INITDB_ARGS=--wal-segsize=16",
  ]
  ports {
    internal = 5432
    external = 5432
  }
  networks_advanced {
    name = docker_network.custom_network.name
    aliases = ["postgres-master"]
  }
  volumes {
    host_path      = "/Users/kimsunjoo/Desktop/spring_study/hello-spring/iac/postgres_master"
    container_path = "/docker-entrypoint-initdb.d"
  }
  command = [
    "postgres",
    "-c", "wal_level=replica",
    "-c", "max_wal_senders=3",
    "-c", "max_replication_slots=3",
    "-c", "hot_standby=on"
  ]
}

# PostgreSQL Slave
# resource "docker_container" "postgres_slave" {
#   image = "postgres:13"
#   name  = "postgres-slave"
#   env = [
#     "POSTGRES_PASSWORD=rootpassword",
#     "POSTGRES_USER=postgres",
#   ]
#   depends_on = [docker_container.postgres_master]
#   networks_advanced {
#     name = docker_network.custom_network.name
#     aliases = ["postgres-slave"]
#   }
#   volumes {
#     host_path      = "/Users/kimsunjoo/Desktop/spring_study/hello-spring/iac/postgres_slave"
#     container_path = "/docker-entrypoint-initdb.d"
#   }
#   command = [
#     "bash", "-c",
#     "until pg_basebackup -h postgres-master -D /var/lib/postgresql/data/pgdata -U postgres -vP -W; do echo 'Retrying base backup'; sleep 1; done && postgres"
#   ]
# }
