# DB Subnet Group
resource "aws_db_subnet_group" "aurora_subnet_group" {
  name = "aurora_subnet_group"
  subnet_ids = data.terraform_remote_state.vpc.outputs.db_private_subnets
  tags = {
    Name = "aurora_subnet_group"
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
  skip_final_snapshot = true
  vpc_security_group_ids = [data.terraform_remote_state.vpc.outputs.aws_security_group_default_id]
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


output "rds_endpoint" {
  value = aws_rds_cluster.aurora_cluster.endpoint
}

# docker-compose.yml 파일에서 자동으로 사용되도록 변수 전달
resource "null_resource" "docker_env_setup" {
  provisioner "local-exec" {
    command = "echo 'DB_HOST=${aws_rds_cluster.aurora_cluster.endpoint}' > .env"
  }
}