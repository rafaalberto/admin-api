terraform {
  backend "s3" {
    bucket = "rafaalberto17-admin-api-terraform-state"
    key = "admin-api"
    region = "us-east-1"
    profile = "terraform"
  }
}