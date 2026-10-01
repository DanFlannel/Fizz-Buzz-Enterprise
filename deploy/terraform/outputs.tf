output "eks_cluster_endpoint" {
  value = aws_eks_cluster.fizzbuzz.endpoint
}

output "audit_db_endpoint" {
  value = aws_db_instance.audit.endpoint
}

output "kafka_bootstrap_brokers" {
  value = aws_msk_cluster.events.bootstrap_brokers_tls
}

output "monthly_cost_estimate" {
  description = "Rough monthly spend to print 100 lines"
  value       = "about $4,800, or $48 per line"
}
