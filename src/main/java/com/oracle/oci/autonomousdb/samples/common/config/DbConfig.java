package com.oracle.oci.autonomousdb.samples.common.config;

import com.oracle.bmc.auth.InstancePrincipalsAuthenticationDetailsProvider;
import com.oracle.bmc.secrets.SecretsClient;
import com.oracle.bmc.secrets.model.Base64SecretBundleContentDetails;
import com.oracle.bmc.secrets.requests.GetSecretBundleRequest;
import com.oracle.bmc.secrets.responses.GetSecretBundleResponse;
import org.apache.commons.codec.binary.Base64;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("oci")
@Primary
@Configuration(proxyBeanMethods=false)
@ConfigurationProperties(prefix = "spring.datasource")
public class DbConfig extends DataSourceProperties {

	private OciConfig ociConfig;

	public DbConfig(OciConfig ociConfig) {
		this.ociConfig = ociConfig;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		String username = getSecreteFromVault(ociConfig.getRegion(), ociConfig.getVault().getDbUsernameOcid());
		this.setUsername(username);
		String password = getSecreteFromVault(ociConfig.getRegion(), ociConfig.getVault().getDbPasswordOcid());
		this.setPassword(password);
	}

	private String getSecreteFromVault(String regionIdString, String secretOcid) {
		GetSecretBundleResponse getSecretBundleResponse;
		try (SecretsClient secretsClient = new SecretsClient(InstancePrincipalsAuthenticationDetailsProvider.builder().build())) {
			secretsClient.setRegion(regionIdString);
			GetSecretBundleRequest getSecretBundleRequest = GetSecretBundleRequest
					.builder()
					.secretId(secretOcid)
					.stage(GetSecretBundleRequest.Stage.Current)
					.build();
			getSecretBundleResponse = secretsClient.getSecretBundle(getSecretBundleRequest);
		}
		Base64SecretBundleContentDetails base64SecretBundleContentDetails =
				(Base64SecretBundleContentDetails) getSecretBundleResponse.getSecretBundle().getSecretBundleContent();
		byte[] secretValueDecoded = Base64.decodeBase64(base64SecretBundleContentDetails.getContent());
		return new String(secretValueDecoded);
	}

}
