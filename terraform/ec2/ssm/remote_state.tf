data "terraform_remote_state" "vpc" {
  backend = "s3"
  config = {
    bucket = "sunjoo-apne2-tfstate"
    key    = "terraform/vpc/terraform.tfstate"
    region = "ap-northeast-2"
  }
}