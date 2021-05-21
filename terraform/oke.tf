resource "oci_containerengine_cluster" "okell_cluster" {
  #Required
  compartment_id     = var.compartment_ocid
  endpoint_config {
    is_public_ip_enabled = "true"
    nsg_ids = [
    ]
    subnet_id = oci_core_subnet.endpoint_Subnet.id
  }
  kubernetes_version = "v1.19.7"
  name               = var.oke_cluster_name
  kms_key_id = var.app_vault_key
  vcn_id             = oci_core_vcn.okell_vcn.id
  #Optional
  options {
    service_lb_subnet_ids = [oci_core_subnet.svclb_Subnet.id]
    #Optional
    add_ons {
      #Optional
      is_kubernetes_dashboard_enabled = "false"
      is_tiller_enabled               = "false"
    }
    admission_controller_options {
      #Optional
      is_pod_security_policy_enabled = "false"
    }
    kubernetes_network_config {
      #Optional
      pods_cidr     = "10.244.0.0/16"
      services_cidr = "10.96.0.0/16"
    }
  }
}
resource "oci_containerengine_node_pool" "okell_node_pool" {
  #Required
  cluster_id         = oci_containerengine_cluster.okell_cluster.id
  compartment_id     = var.compartment_ocid
  kubernetes_version = "v1.19.7"
  name               = "app-node-pool"
  node_shape         = "VM.Standard.E2.1"
  node_config_details {
    placement_configs {
      availability_domain = data.oci_identity_availability_domain.export_AuTH-EU-FRANKFURT-1-AD-1.name
      subnet_id           = oci_core_subnet.nodePool_Subnet.id
    }
    size = "1"
  }
  node_source_details {
    #Required
    image_id    = local.oracle_linux_images.0 # Latest
    source_type = "IMAGE"
    #Optional
  }
}
data "oci_containerengine_cluster_option" "okell_cluster_option" {
  cluster_option_id = "all"
}
data "oci_containerengine_node_pool_option" "okell_node_pool_option" {
  node_pool_option_id = "all"
}
locals {
  all_sources = data.oci_containerengine_node_pool_option.okell_node_pool_option.sources
  oracle_linux_images = [for source in local.all_sources : source.image_id if length(regexall("Oracle-Linux-[0-9]*.[0-9]*-20[0-9]*",source.source_name)) > 0]
}

output "cluster_kubernetes_versions" {
  value = [data.oci_containerengine_cluster_option.okell_cluster_option.kubernetes_versions]
}
output "node_pool_kubernetes_version" {
  value = [data.oci_containerengine_node_pool_option.okell_node_pool_option.kubernetes_versions]
}
data "oci_containerengine_cluster_kube_config" "okell_cluster_kube_config" {
  #Required
  cluster_id = oci_containerengine_cluster.okell_cluster.id
  #Optional
  token_version = "2.0.0"
}
resource "local_file" "okell_cluster_kube_config_file" {
  content  = data.oci_containerengine_cluster_kube_config.okell_cluster_kube_config.content
  filename = "${path.module}/kubeconfig"
}
