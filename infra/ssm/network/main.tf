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
resource "aws_vpc" "ssm_vpc" {
  # VPC CIDR Block 16 ip
  cidr_block = "10.0.0.0/27"
  enable_dns_support = true
  enable_dns_hostnames = true
  tags = {
    Name = "ssm_vpc"
  }
}

# private subnet
resource "aws_subnet" "private_subnet" {
  availability_zone = "ap-northeast-2a"
  # VPC CIDR Block 16 ip
  cidr_block = "10.0.0.16/28"
  vpc_id = aws_vpc.ssm_vpc.id
  tags = {
    Name = "private-subnet"
  }
}

resource "aws_route_table" "private_table" {
  vpc_id = aws_vpc.ssm_vpc.id
}

resource "aws_vpc_endpoint" "ssm" {
  vpc_id       = aws_vpc.ssm_vpc.id
  subnet_ids = [aws_subnet.private_subnet.id]
  service_name = "com.amazonaws.ap-northeast-2.ssm"
  security_group_ids = [aws_security_group.ssm_security_group.id]
  private_dns_enabled = true
  vpc_endpoint_type = "Interface"
}

resource "aws_vpc_endpoint" "ssmmessages" {
  vpc_id       = aws_vpc.ssm_vpc.id
  subnet_ids = [aws_subnet.private_subnet.id]
  service_name = "com.amazonaws.ap-northeast-2.ssmmessages"
  security_group_ids = [aws_security_group.ssm_security_group.id]
  private_dns_enabled = true
  vpc_endpoint_type = "Interface"
}

resource "aws_vpc_endpoint" "ec2messages" {
  vpc_id       = aws_vpc.ssm_vpc.id
  subnet_ids = [aws_subnet.private_subnet.id]
  service_name = "com.amazonaws.ap-northeast-2.ec2messages"
  security_group_ids = [aws_security_group.ssm_security_group.id]
  private_dns_enabled = true
  vpc_endpoint_type = "Interface"
}

resource "aws_security_group" "ssm_security_group" {
  vpc_id = aws_vpc.ssm_vpc.id
  ingress {
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port = 443
    to_port = 443
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port = 443
    to_port = 443
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

output "ssm_security_group_id" {
  value = aws_security_group.ssm_security_group.id
}

output "private_subnet_id" {
  value = aws_subnet.private_subnet.id
}

output "vpc_id" {
  value = aws_vpc.ssm_vpc.id
}
