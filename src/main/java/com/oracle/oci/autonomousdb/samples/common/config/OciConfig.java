package com.oracle.oci.autonomousdb.samples.common.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author bnasslahsen
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "oci.tenant")
@Configuration(proxyBeanMethods=false)
public class OciConfig {

	private String region;

	private Vault vault= new Vault();

	@Data
	@NoArgsConstructor
	public static class Vault {

		private String dbUsernameOcid;

		private String dbPasswordOcid;
	}

}
