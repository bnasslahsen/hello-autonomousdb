/**

This class is responsible for configuring the Oracle Autonomous Database connection properties using VaultClient.
It extends DataSourceProperties to customize the connection properties of the Spring Data source.
It uses VaultClient to retrieve the database username and password from Oracle Cloud Infrastructure (OCI) Vault service.
@author dralquinta
@see VaultClient
@see DataSourceProperties
*/

package com.oracle.oci.autonomousdb.samples.common.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("oci")
@Primary
@Configuration(proxyBeanMethods=false)
@ConfigurationProperties(prefix = "spring.datasource")
@Component
public class DbConfig extends DataSourceProperties {
	private final VaultClient vc;

/**
 * Constructor for DbConfig that takes VaultClient as an input parameter.
 * 
 * @param vc a VaultClient instance to retrieve database credentials from OCI Vault service.
 */
	public DbConfig(VaultClient vc) {
        this.vc = vc;
    }
	
/**
 * Overrides the method afterPropertiesSet() of DataSourceProperties to customize the connection properties 
 * of the Spring Data source.
 * It uses VaultClient to retrieve the database username and password from Oracle Cloud Infrastructure (OCI) Vault service.
 * 
 * @throws Exception if there is an issue with setting the database username and password.
 */
	@Override
	public void afterPropertiesSet() throws Exception {
		
		super.afterPropertiesSet();		
		String username = vc.getUsernameFromVault();
		this.setUsername(username);	
			
		String password = vc.getPasswordFromVault();
		this.setPassword(password);
	}


}
