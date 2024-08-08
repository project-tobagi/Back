# main.tf

# Terraform 및 Docker provider 설정
terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 2.13"
    }
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
  }
  required_version = ">= 1.0.0"
}
# Docker provider 설정
provider "docker" {
  host = "unix:///var/run/docker.sock"
}
# AWS provider 설정 ( 서울 리전 선택)
provider "aws" {
  region = "ap-northeast-2"
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
# AWS SSH 키 페어 설정(todo)
resource "aws_key_pair" "deployer" {
    key_name   = "tobagi-deployer-key"
    public_key = file("~/.ssh/id_rsa.pub")
}

  # AWS 보안 그룹 설정 (SSH, HTTP, HTTPS 허용)
resource "aws_security_group" "web_sg" {
    name        = "allow_ssh_http_https"
    description = "Allow SSH, HTTP, and HTTPS inbound traffic"

    ingress {
      from_port   = 22
      to_port     = 22
      protocol    = "tcp"
      cidr_blocks = ["0.0.0.0/0"]  # SSH: 모든 IP 접근 허용 (todo)
    }

    ingress {
      from_port   = 80
      to_port     = 80
      protocol    = "tcp"
      cidr_blocks = ["0.0.0.0/0"]  # HTTP: 모든 IP 접근 허용
    }

    ingress {
      from_port   = 443
      to_port     = 443
      protocol    = "tcp"
      cidr_blocks = ["0.0.0.0/0"]  # HTTPS: 모든 IP 접근 허용
    }

    egress {
      from_port   = 0
      to_port     = 0
      protocol    = "-1"  # 모든 아웃바운드 트래픽 허용
      cidr_blocks = ["0.0.0.0/0"]
    }
}

# 네트워크 인터페이스 생성
resource "aws_network_interface" "eni" {
  subnet_id   = "subnet-0123456789abcdef0"  # 서브넷 ID 수정 (사용하려는 서브넷 ID로 변경)
  # private_ips = ["10.0.1.100"]  # 프라이빗 IP 수정 (자동 할당을 원하면 제거 가능)
  security_groups = [aws_security_group.web_sg.id]  # 보안 그룹 ID
}

  # AWS EC2 인스턴스 설정
resource "aws_instance" "web" {
    ami           = "ami-0742b4e673072066f"  # 9. Windows Server 2019 AMI ID (todo)
    instance_type = "t2.micro"  # 무료 티어
    key_name      = aws_key_pair.deployer.key_name  # 생성한 키 페어 사용

    # 보안 그룹 지정
    vpc_security_group_ids = [aws_security_group.web_sg.id]

    # EC2 인스턴스 시작 시 실행할 사용자 데이터 스크립트
    user_data = <<-EOF
              #!/bin/bash
              sudo apt-get update
              sudo apt-get install -y openjdk-11-jdk
              EOF
  # 네트워크 인터페이스 연결
    network_interface {
      network_interface_id = aws_network_interface.eni.id  # ENI ID 연결 (생성한 ENI 리소스의 ID)
      device_index         = 0  # 첫 번째 네트워크 인터페이스로 설정
    }
    tags = {
      Name = "TobagiApp" #todo
    }
}

# 출력
output "eni_id" {
  description = "The ID of the ENI"
  value       = aws_network_interface.eni.id
}

  # 출력: EC2 인스턴스의 퍼블릭 IP
output "ec2_public_ip" {
    description = "Public IP of the EC2 instance"
    value       = aws_instance.web.public_ip
}
