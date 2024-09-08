terraform {
  backend "s3" {
    bucket         = "sunjoo-apne2-tfstate"                               # name of S3 bucket
    key            = "terraform/hello/sunjood_apnortheast2/terraform.tfstate"
    region         = "ap-northeast-2"
    encrypt        = true
    dynamodb_table = "terraform-lock"
  }
}
