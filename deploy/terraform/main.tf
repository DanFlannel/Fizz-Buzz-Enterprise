locals {
  name = "fizzbuzz-enterprise-${var.environment}"
  tags = {
    Product     = "FizzBuzz Enterprise Edition"
    CostCenter  = "modulo"
    Environment = var.environment
  }
}

# Kubernetes, for the six microservices.
resource "aws_eks_cluster" "fizzbuzz" {
  name     = local.name
  role_arn = var.cluster_role_arn

  vpc_config {
    subnet_ids = var.private_subnet_ids
  }

  tags = local.tags
}

# PostgreSQL, for immutable audit history of every number ever evaluated.
resource "aws_db_subnet_group" "audit" {
  name       = "${local.name}-audit"
  subnet_ids = var.private_subnet_ids
  tags       = local.tags
}

resource "aws_db_instance" "audit" {
  identifier                = "${local.name}-audit"
  engine                    = "postgres"
  engine_version            = "16.4"
  instance_class            = "db.r6g.2xlarge"
  allocated_storage         = 1000
  db_name                   = "fizzbuzz_audit"
  username                  = "fizzbuzz"
  password                  = var.audit_db_password
  db_subnet_group_name      = aws_db_subnet_group.audit.name
  multi_az                  = true
  storage_encrypted         = true
  backup_retention_period   = 35
  deletion_protection       = true
  skip_final_snapshot       = false
  final_snapshot_identifier = "${local.name}-audit-final"
  tags                      = local.tags
}

# Kafka, in case another service needs to know that 45 was FizzBuzz.
resource "aws_msk_cluster" "events" {
  cluster_name           = "${local.name}-events"
  kafka_version          = "3.6.0"
  number_of_broker_nodes = 3

  broker_node_group_info {
    instance_type   = "kafka.m5.large"
    client_subnets  = slice(var.private_subnet_ids, 0, 3)
    security_groups = [aws_security_group.data_plane.id]
  }

  tags = local.tags
}

# Redis, because someone mentioned latency. See ADR-0004.
resource "aws_elasticache_subnet_group" "cache" {
  name       = "${local.name}-cache"
  subnet_ids = var.private_subnet_ids
}

resource "aws_elasticache_replication_group" "resolution_cache" {
  replication_group_id       = "${local.name}-cache"
  description                = "Caches the answer to n % 3"
  engine                     = "redis"
  node_type                  = "cache.r6g.large"
  num_cache_clusters         = 3
  automatic_failover_enabled = true
  subnet_group_name          = aws_elasticache_subnet_group.cache.name
  security_group_ids         = [aws_security_group.data_plane.id]
  tags                       = local.tags
}

resource "aws_security_group" "data_plane" {
  name        = "${local.name}-data-plane"
  description = "Allows FizzBuzz to talk to itself"
  vpc_id      = var.vpc_id
  tags        = local.tags
}
