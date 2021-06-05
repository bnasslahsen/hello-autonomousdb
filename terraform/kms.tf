## OCI VAULT
resource oci_kms_vault app_vault {
  compartment_id = var.compartment_ocid
  display_name = "app-vault"
  freeform_tags = {
  }
  vault_type = "DEFAULT"
}

resource oci_kms_key app_vault_key {
  compartment_id = var.compartment_ocid
  defined_tags = {
  }
  desired_state = "ENABLED"
  display_name  = "app-key"
  freeform_tags = {
  }
  key_shape {
    algorithm = "AES"
    curve_id  = ""
    length    = "32"
  }
  management_endpoint = oci_kms_vault.app_vault.management_endpoint
  protection_mode     = "HSM"
}

# Outputs for kms
output "app_vault_key_id" {
  value = oci_kms_key.app_vault_key.id
}
