# main.tf

# Terraform 및 Docker provider 설정
terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 2.13"
    }
  }
  required_version = ">= 1.0.0"
}

provider "docker" {
  host = "unix:///var/run/docker.sock"
}

# MySQL Docker 이미지 설정
resource "docker_image" "mysql" {
  name = "mysql:8.0"
}

# MySQL 컨테이너 설정
resource "docker_container" "mysql_container" {
  image = docker_image.mysql.latest
  name  = "my_mysql_container"

  # MySQL 환경 변수 설정
  env = [
    "MYSQL_ROOT_PASSWORD=root_password",
    "MYSQL_DATABASE=my_database",
    "MYSQL_USER=my_user",
    "MYSQL_PASSWORD=my_password",
  ]

  # 포트 매핑
  ports {
    internal = 3306
    external = 3306
  }

  # init.sql 파일을 컨테이너에 마운트
  volumes {
    host_path      = "/Users/kimsunjoo/Desktop/spring_study/hello-spring/init.sql"
    container_path = "/docker-entrypoint-initdb.d/init.sql"
  }
}
