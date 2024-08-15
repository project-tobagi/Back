terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.62.0"
    }
  }
}

provider "aws" {
  region = "ap-northeast-2"
}

# vpc
resource "aws_vpc" "db_vpc" {
  # VPC CIDR Block 16 ip
  cidr_block = "10.0.0.0/22"
  enable_dns_support = true
  enable_dns_hostnames = true
  tags = {
    Name = "ssm_vpc"
  }
}

# Subnets
resource "aws_subnet" "aurora_subnet_1" {
  vpc_id = aws_vpc.db_vpc.id
  cidr_block = "10.0.1.0/28"
  availability_zone = "ap-northeast-2a"
  tags = {
    Name = "rds_subnet_1"
  }
}

resource "aws_subnet" "aurora_subnet_2" {
  vpc_id = aws_vpc.db_vpc.id
  cidr_block = "10.0.2.0/26"
  availability_zone = "ap-northeast-2b"
  tags = {
    Name = "rds_subnet_2"
  }
}

# DB Subnet Group
resource "aws_db_subnet_group" "aurora_subnet_group" {
  name = "aurora_subnet_group"
  subnet_ids = [aws_subnet.aurora_subnet_1.id, aws_subnet.aurora_subnet_2.id]
  tags = {
    Name = "aurora_subnet_group"
  }
}

# Security Group
resource "aws_security_group" "aurora_sg" {
  vpc_id = aws_vpc.db_vpc.id
  ingress {
    from_port = 3306
    to_port = 3306
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
  tags = {
    Name = "aurora_sg"
  }
}

# Aurora Cluster
resource "aws_rds_cluster" "aurora_cluster" {
  cluster_identifier = "aurora-cluster"
  engine = "aurora-mysql"
  engine_version = "8.0"
  master_username = "admin"
  master_password = "password"
  db_subnet_group_name = aws_db_subnet_group.aurora_subnet_group.name
  vpc_security_group_ids = [aws_security_group.aurora_sg.id]
  skip_final_snapshot = true
  tags = {
    Name = "aurora_cluster"
  }
}

# Aurora Cluster Instances
resource "aws_rds_cluster_instance" "aurora_cluster_instance_1" {
  identifier = "aurora-cluster-instance-1"
  cluster_identifier = aws_rds_cluster.aurora_cluster.id
  instance_class = "db.t3.medium"
  engine = aws_rds_cluster.aurora_cluster.engine
  engine_version = aws_rds_cluster.aurora_cluster.engine_version
  db_subnet_group_name = aws_db_subnet_group.aurora_subnet_group.name
  tags = {
    Name = "aurora_cluster_instance_1"
  }
}

resource "aws_rds_cluster_instance" "aurora_cluster_instance_2" {
  identifier = "aurora-cluster-instance-2"
  cluster_identifier = aws_rds_cluster.aurora_cluster.id
  instance_class = "db.t3.medium"
  engine = aws_rds_cluster.aurora_cluster.engine
  engine_version = aws_rds_cluster.aurora_cluster.engine_version
  db_subnet_group_name = aws_db_subnet_group.aurora_subnet_group.name
  tags = {
    Name = "aurora_cluster_instance_2"
  }
}