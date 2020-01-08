resource "aws_db_instance" "return_delivery_db" {
  allocated_storage = 20
  max_allocated_storage = 100
  storage_type = "gp2"
  name = var.db_name
  engine = "oracle-se2"
  engine_version = "19.0.0.0"
  instance_class = "db.t2.micro"
  multi_az = true
  vpc_security_group_ids = [aws_security_group.return_delivery_db_public_sec_group.id]

  username = var.db_user
  password = var.db_password
}

resource "aws_security_group" "return_delivery_db_public_sec_group" {
  ingress {
    from_port = 1521
    protocol = ""
    to_port = 1521
    cidr_blocks = ["0.0.0.0/0"]
  }
}