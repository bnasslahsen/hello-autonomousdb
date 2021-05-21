## Common vars
variable region {
  default = "eu-frankfurt-1"
}

variable fingerprint {
  default = "80:4d:5d:d1:87:b0:2b:09:9a:95:b6:5a:08:35:80:0b"
}

variable tenancy_ocid {
  default = "ocid1.tenancy.oc1..aaaaaaaaqgkxttllzbr2ubzgmwc52o4bbgsukswrasydemspwdhgek4i45zq"
}

variable user_ocid {
  default = "ocid1.user.oc1..aaaaaaaabmxyqwtm57vzsslhen6zemjmr6hr5masolwhfq7ugmn6fjnj7pjq"
}

variable private_key_path {
  default = "/Users/bnasslah/.oci/oci_api_key.pem"
}

## Compartment name
variable compartment_ocid {
  default = "ocid1.compartment.oc1..aaaaaaaawv7ydwkdkpebps24g2rvbb3znq3xfcdmuv4btnqs5vqiegcd6i6q"
}

variable operator_timezone {
  default ="Europe/Paris"
}

variable db_name {
  default = "HELLOATP"
}

variable db_password {
  default = "HelloDB+2021"
}

variable app_vault_key {
  default = "ocid1.key.oc1.eu-frankfurt-1.bjqjh77haafak.abtheljs4gf2iuvsjjyp4biughl5tft7mxgwvup2l262ao27k7g6dlt3yvfq"
}

variable oke_cluster_name {
  default = "app-cluster"
}