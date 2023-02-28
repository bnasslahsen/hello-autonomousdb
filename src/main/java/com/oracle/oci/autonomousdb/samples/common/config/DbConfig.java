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

	public DbConfig(VaultClient vc) {
        this.vc = vc;
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		
		super.afterPropertiesSet();		
		String username = vc.getUsernameFromVault();
		this.setUsername(username);	
			
		String password = vc.getPasswordFromVault();
		this.setPassword(password);
	}


}
