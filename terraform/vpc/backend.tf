terraform {
    backend "s3" {
      bucket         = "sunjoo-apne2-tfstate" # name of S3 bucket
      key            = "terraform/vpc/terraform.tfstate" # This is the path where it is stored within S3.
      region         = "ap-northeast-2"  
      encrypt        = true
      dynamodb_table = "terraform-lock"
    }
}


