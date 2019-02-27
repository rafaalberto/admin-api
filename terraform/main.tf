provider "aws" {
  version = "~> 2.0"
  shared_credentials_file = "~/.aws/credentials"
  profile = "terraform"
}