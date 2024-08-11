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

# AWS IAM 사용자 생성
resource "aws_iam_user" "admin_user" {
  name = "admin_user"
}

# AWS IAM 정책 첨부
resource "aws_iam_user_policy_attachment" "admin_user_policy" {
  user       = aws_iam_user.admin_user.name
  policy_arn = "arn:aws:iam::aws:policy/AdministratorAccess"
}