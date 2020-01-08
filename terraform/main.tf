provider "aws" {
  region = "eu-west-1"
}

resource "aws_lambda_function" "return_delivery_lambda" {
  function_name = "return_delivery_lambda"
  handler = "handleReturnDelivery"
  filename = "target/buprototype-1.0-aws.jar"
  description = "Lambda that handles return deliveries"
  runtime = "java8"
  role = aws_iam_role.return_delivery_lambda_iam.arn
  source_code_hash = base64sha256(file("target/buprototype-1.0-aws.jar"))
  environment {
    variables {
      DATASOURCE_URL = aws_db_instance.return_delivery_db.address
      DATASOURCE_USER = var.db_user
      DATASOURCE_PASS = var.db_password

      SUPPLIER_CHARGE_BACK_SERVICE_URL = var.supplier_charge_back_service_url
      CUSTOMER_CREDIT_NOTE_SERVICE_URL = var.customer_credit_note_service_url
    }
  }
}

resource "aws_iam_role" "return_delivery_lambda_iam" {
  name = "iam_for_lambda"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF
}