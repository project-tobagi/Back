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
  image = docker_image.mysql.image_id
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
# AWS SSH 키 페어 설정
resource "aws_key_pair" "deployer" {
    key_name   = "tobagi-deployer-key"
    public_key = file("~/.ssh/id_rsa.pub")
}

resource "aws_vpc" "main" {
  cidr_block = "10.0.0.0/16"
  tags = {
    Name = "main_vpc"
  }
}
resource "aws_subnet" "main" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "10.0.1.0/24"
  availability_zone = "ap-northeast-2a"
  tags = {
    Name = "main_subnet"
  }
}
  # AWS 보안 그룹 설정 (SSH, HTTP, HTTPS 허용)
resource "aws_security_group" "web_sg" {
    name        = "allow_ssh_http_https"
    description = "Allow SSH, HTTP, and HTTPS inbound traffic"
    vpc_id      = aws_vpc.main.id
    ingress {
      from_port   = 22
      to_port     = 22
      protocol    = "tcp"
      cidr_blocks = ["0.0.0.0/0"]  # SSH: 모든 IP 접근 허용
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
  subnet_id   = aws_subnet.main.id  # 서브넷 ID 수정 (사용하려는 서브넷 ID로 변경)
  # private_ips = ["10.0.1.100"]  # 프라이빗 IP 수정 (자동 할당을 원하면 제거 가능)
  security_groups = [aws_security_group.web_sg.id]  # 보안 그룹 ID
}

# IAM 역할 생성
resource "aws_iam_role" "ec2_role" {
  name = "ec2_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      }
    ]
  })
}
# IAM 인스턴스 프로필 생성
resource "aws_iam_instance_profile" "ec2_profile" {
  name = "ec2_profile"
  role = aws_iam_role.ec2_role.name
}
# IAM 정책 첨부
resource "aws_iam_role_policy_attachment" "ec2_role_policy" {
  role       = aws_iam_role.ec2_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2FullAccess"
}

  # AWS EC2 인스턴스 설정
resource "aws_instance" "web" {
    ami           = "ami-0c2acfcb2ac4d02a0"  # 리눅스 (64비트(x86), uefi-preferred)
    instance_type = "t2.micro"  # 무료 티어
    key_name      = aws_key_pair.deployer.key_name  # 생성한 키 페어 사용
    # IAM 역할 연결
    iam_instance_profile = aws_iam_instance_profile.ec2_profile.name
    # 보안 그룹 지정
    vpc_security_group_ids = [aws_security_group.web_sg.id]
    subnet_id              = aws_subnet.main.id

    # EC2 인스턴스 시작 시 실행할 사용자 데이터 스크립트
    user_data = <<-EOF
              #!/bin/bash
              sudo apt-get update
              sudo apt-get install -y openjdk-11-jdk
              EOF
#   네트워크 인터페이스 연결
#    network_interface {
#      network_interface_id = aws_network_interface.eni.id  # ENI ID 연결 (생성한 ENI 리소스의 ID)
#      device_index         = 0  # 첫 번째 네트워크 인터페이스로 설정
#    }
    tags = {
      Name = "TobagiApp"
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