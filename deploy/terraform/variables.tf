variable "region" {
  description = "Region in which to compute divisibility"
  type        = string
  default     = "us-east-1"
}

variable "environment" {
  description = "Deployment stage"
  type        = string
  default     = "prod"
}

variable "vpc_id" {
  description = "VPC for the FizzBuzz data plane"
  type        = string
}

variable "private_subnet_ids" {
  description = "At least three subnets, for a highly available 15"
  type        = list(string)
}

variable "cluster_role_arn" {
  description = "IAM role for the EKS control plane"
  type        = string
}

variable "audit_db_password" {
  description = "Password for the immutable audit history database"
  type        = string
  sensitive   = true
}
