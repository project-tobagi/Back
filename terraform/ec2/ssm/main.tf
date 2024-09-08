# SSM
resource "aws_vpc_endpoint" "ssm" {
  vpc_id       = data.terraform_remote_state.vpc.outputs.vpc_id
  subnet_ids = [data.terraform_remote_state.vpc.outputs.private_subnets[0]] // [data.terraform_remote_state.vpc.outputs.private_subnets[0]]
  service_name = "com.amazonaws.ap-northeast-2.ssm"
  security_group_ids = [aws_security_group.ssm_security_group.id]
  private_dns_enabled = true
  vpc_endpoint_type = "Interface"
}

resource "aws_vpc_endpoint" "ssmmessages" {
  vpc_id       =  data.terraform_remote_state.vpc.outputs.vpc_id
  subnet_ids = [data.terraform_remote_state.vpc.outputs.private_subnets[0]]
  service_name = "com.amazonaws.ap-northeast-2.ssmmessages"
  security_group_ids = [aws_security_group.ssm_security_group.id]
  private_dns_enabled = true
  vpc_endpoint_type = "Interface"
}

resource "aws_vpc_endpoint" "ec2messages" {
  vpc_id       =  data.terraform_remote_state.vpc.outputs.vpc_id
  subnet_ids = [data.terraform_remote_state.vpc.outputs.private_subnets[0]]
  service_name = "com.amazonaws.ap-northeast-2.ec2messages"
  security_group_ids = [aws_security_group.ssm_security_group.id]
  private_dns_enabled = true
  vpc_endpoint_type = "Interface"
}

resource "aws_security_group" "ssm_security_group" {
  vpc_id =  data.terraform_remote_state.vpc.outputs.vpc_id
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
  egress {
    from_port = 3306
    to_port = 3306
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
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
//
# RDS 읽기 전용 접근 정책 연결
resource "aws_iam_role_policy_attachment" "rds_readonly_policy_attachment" {
  role       = aws_iam_role.role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonRDSReadOnlyAccess"
}

resource "aws_iam_role_policy_attachment" "ssm_policy_attachment" {
  role       = aws_iam_role.role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}

resource "aws_iam_instance_profile" "profile" {
  name = "ec2_instance_profile"
  role = aws_iam_role.role.name
}

# resource "aws_instance" "ssm_ec2" {
#   ami = "ami-0c2acfcb2ac4d02a0"
#   instance_type = "t2.nano"
#   vpc_security_group_ids = [aws_security_group.ssm_security_group.id]
#   subnet_id = data.terraform_remote_state.vpc.outputs.private_subnets[0]
#   iam_instance_profile = aws_iam_instance_profile.profile.name
#   tags = {
#     Name = "ssm"
#   }
# }
resource "aws_instance" "ssm_ec2" {
  ami                    = "ami-0c2acfcb2ac4d02a0"
  instance_type          = "t2.nano"
  vpc_security_group_ids = [aws_security_group.ssm_security_group.id]
  subnet_id              = data.terraform_remote_state.vpc.outputs.private_subnets[0]
  iam_instance_profile   = aws_iam_instance_profile.profile.name

  user_data = <<-EOF
    #!/bin/bash
    sudo apt-get update -y
    sudo apt-get install -y docker.io
    sudo systemctl start docker
    sudo systemctl enable docker

    # Docker Compose 설치
    sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose

    # GitHub에서 파일 클론
    git clone https://github.com/project-tobagi/Back.git

    # Docker Compose 실행
    # Docker Compose 실행
    cd /home/ec2-user/app
    sudo docker-compose up -d
  EOF

  tags = {
    Name = "ssm"
  }
}
