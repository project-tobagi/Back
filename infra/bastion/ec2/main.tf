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

data "terraform_remote_state" "network" {
  backend = "local"
  config = {
    path = "../network/terraform.tfstate"
  }
}

resource "aws_iam_role" "role" {
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

resource "aws_iam_role_policy_attachment" "ssm_policy_attachment" {
  role       = aws_iam_role.role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}

resource "aws_iam_instance_profile" "profile" {
  name = "ec2_instance_profile"
  role = aws_iam_role.role.name
}

resource "aws_instance" "bastion" {
  ami = "ami-0c2acfcb2ac4d02a0"
  instance_type = "t2.nano"
  vpc_security_group_ids = [data.terraform_remote_state.network.outputs.bastion_security_group_id]
  subnet_id = data.terraform_remote_state.network.outputs.private_subnet_id
  iam_instance_profile = aws_iam_instance_profile.profile.name
  tags = {
    Name = "bastion"
  }
}
