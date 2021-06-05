resource oci_database_autonomous_database hello_db {
  compartment_id = var.compartment_ocid
  cpu_core_count = "1"
  data_safe_status = "NOT_REGISTERED"
  data_storage_size_in_tbs = "1"
  db_name = var.db_name
  db_version = "19c"
  db_workload = "OLTP"
  admin_password = var.db_password
  display_name = var.db_name
  is_auto_scaling_enabled = "true"
  is_data_guard_enabled = "false"
  is_dedicated = "false"
  is_free_tier = "false"
  license_model = "LICENSE_INCLUDED"
  nsg_ids = []
  open_mode = "READ_WRITE"
  operations_insights_status = "NOT_ENABLED"
  permission_level = "UNRESTRICTED"
  state = "AVAILABLE"
  whitelisted_ips = []
}
