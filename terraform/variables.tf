variable "db_name" {
  type = "string"
  description = "Database name"
  default = "RETURN_DELIVERY"
}

variable "db_password" {
  type = "string"
  description = "Database password"
}

variable "db_user" {
  type = "string"
  description = "Database user"
}

variable "supplier_charge_back_service_url" {
  type = "string"
  description = "Supplier charge back service address"
}

variable "customer_credit_note_service_url" {
  type = "string"
  description = "Customer credit note service address"
}