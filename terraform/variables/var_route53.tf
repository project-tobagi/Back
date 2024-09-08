variable "r53_variables" {
  default = {
    id = {
      star_sunjoo_com_acm_arn_apnortheast2 = "arn:aws:acm:ap-northeast-2:120569629942:certificate/800e4247-5262-4085-8a40-af907929292d"
      sunjoo_com_zone_id                   = "Z07612222KB3J9LFS8FTL"
    }
    prod = {

      prod_devopsartfactory_com_zone_id                   = "Z048397936KDDQS9NZSTU"
      star_prod_devopsartfactory_com_acm_arn_apnortheast2 = "arn:aws:acm:ap-northeast-2:002202845208:certificate/b440dccd-4d95-4313-bc0d-f25f2b7648c3"
      www_devopsartfactory_com_acm_arn_useast1            = ""
    }
  }
}

