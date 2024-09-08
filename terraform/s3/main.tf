provider "aws" {
  region = "ap-northeast-2"
}

resource "aws_s3_bucket" "my_bucket" {
  bucket = "sunjoo-apne2-api-bucket"  # 고유한 버킷 이름

  tags = {
    Name        = "api-bucket"
    Environment = "Dev"
  }
}

output "bucket_name" {
  value = aws_s3_bucket.my_bucket.id
}