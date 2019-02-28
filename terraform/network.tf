resource "aws_vpc" "main" {
  cidr_block = "192.168.0.0/16"

  tags {
    Name = "main"
  }
}

resource "aws_subnet" "private_subnet" {
  count = 3

  cidr_block = "${cidrsubnet(aws_vpc.main.cidr_block, 8, count.index + 10)}"

  vpc_id = "${aws_vpc.main.id}"
  availability_zone = "${var.availability_zones[count.index]}"

  tags {
    Name = "private_subnet_${count.index}"
  }
}

resource "aws_subnet" "public_subnet" {
  count = 3

  cidr_block = "${cidrsubnet(aws_vpc.main.cidr_block, 8, count.index + 20)}"

  vpc_id = "${aws_vpc.main.id}"
  availability_zone = "${var.availability_zones[count.index]}"
  map_public_ip_on_launch = true

  tags {
    Name = "public_subnet_${count.index}"
  }
}