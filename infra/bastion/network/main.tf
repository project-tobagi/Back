terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.62.0"
    }
  }
}
# AWS Provider 설정
provider "aws" {
  region = "ap-northeast-2"
}

# vpc
resource "aws_vpc" "bastion_vpc" {
  # VPC CIDR Block 16 ip
  cidr_block = "10.0.0.0/27"
  enable_dns_support = true
  enable_dns_hostnames = true
  tags = {
    Name = "bastion_vpc"
  }
}

# public 서브넷은 필요가 없음
# 대신 vpc endpoint를 사용해서 ssm을 이용한 접속을 구현해야함
# https://docs.aws.amazon.com/systems-manager/latest/userguide/session-manager-getting-started-privatelink.html
# private subnet
resource "aws_subnet" "private_subnet" {
  availability_zone = "ap-northeast-2a"
  # VPC CIDR Block 16 ip
  cidr_block = "10.0.0.16/28"
  vpc_id = aws_vpc.bastion_vpc.id
  map_public_ip_on_launch = false
  tags = {
    Name = "private-subnet"
  }
}

resource "aws_vpc_endpoint" "ssm" {
  vpc_id       = aws_vpc.bastion_vpc.id
  service_name = "com.amazonaws.ap-northeast-2.ssm"
  vpc_endpoint_type = "Interface"
}

resource "aws_vpc_endpoint" "ssmmessages" {
  vpc_id       = aws_vpc.bastion_vpc.id
  service_name = "com.amazonaws.ap-northeast-2.ssmmessages"
  vpc_endpoint_type = "Interface"
}

resource "aws_vpc_endpoint" "ec2messages" {
  vpc_id       = aws_vpc.bastion_vpc.id
  service_name = "com.amazonaws.ap-northeast-2.ec2messages"
  vpc_endpoint_type = "Interface"
}

resource "aws_security_group" "bastion_security_group" {
  vpc_id = aws_vpc.bastion_vpc.id
  ingress {
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = ["211.207.252.32/32"]
  }
  ingress {
    from_port = 443
    to_port = 443
    protocol = "tcp"
    cidr_blocks = ["211.207.252.32/32"]
  }
  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["211.207.252.32/32"]
  }
}

output "bastion_security_group_id" {
  value = aws_security_group.bastion_security_group.id
}

output "private_subnet_id" {
  value = aws_subnet.private_subnet.id
}