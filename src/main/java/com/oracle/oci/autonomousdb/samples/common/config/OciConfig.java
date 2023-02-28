package com.oracle.oci.autonomousdb.samples.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**

A configuration class for Oracle Cloud Infrastructure (OCI) configuration properties related to a vault storing

database credentials.
*/


@Configuration
@ConfigurationProperties(prefix = "oci.tenant.vault")
public class OciConfig {

/**
The OCID of the secret in the vault containing the database username.
*/
    private String dbUsernameOcid;

/**
The OCID of the secret in the vault containing the database password.
*/
    private String dbPasswordOcid;

/**
Getter for the OCID of the secret in the vault containing the database username.
@return The OCID of the secret in the vault containing the database username.
*/

    public String getDbUsernameOcid() {
        return dbUsernameOcid;
    }

/**
Setter for the OCID of the secret in the vault containing the database username.
@param dbUsernameOcid The OCID of the secret in the vault containing the database username.
*/

    public void setDbUsernameOcid(String dbUsernameOcid) {
        this.dbUsernameOcid = dbUsernameOcid;
    }

/**
Getter for the OCID of the secret in the vault containing the database password.
@return The OCID of the secret in the vault containing the database password.
*/

    public String getDbPasswordOcid() {
        return dbPasswordOcid;
    }

 /**
Setter for the OCID of the secret in the vault containing the database password.
@param dbPasswordOcid The OCID of the secret in the vault containing the database password.
*/   

    public void setDbPasswordOcid(String dbPasswordOcid) {
        this.dbPasswordOcid = dbPasswordOcid;
    }
}