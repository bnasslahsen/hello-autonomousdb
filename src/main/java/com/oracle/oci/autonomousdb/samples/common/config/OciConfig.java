package com.oracle.oci.autonomousdb.samples.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oci.tenant.vault")
public class OciConfig {

    private String dbUsernameOcid;
    private String dbPasswordOcid;

    public String getDbUsernameOcid() {
        return dbUsernameOcid;
    }

    public void setDbUsernameOcid(String dbUsernameOcid) {
        this.dbUsernameOcid = dbUsernameOcid;
    }

    public String getDbPasswordOcid() {
        return dbPasswordOcid;
    }

    public void setDbPasswordOcid(String dbPasswordOcid) {
        this.dbPasswordOcid = dbPasswordOcid;
    }
}