package com.oracle.oci.autonomousdb.samples.common.config;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.bouncycastle.util.encoders.Base64;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.secrets.SecretsClient;
import com.oracle.bmc.auth.BasicAuthenticationDetailsProvider;
import com.oracle.bmc.secrets.responses.GetSecretBundleResponse;


import com.oracle.bmc.secrets.requests.GetSecretBundleRequest;
import com.oracle.bmc.secrets.model.Base64SecretBundleContentDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

@Component
public class VaultClient {

    @Autowired
    private Environment env;

    @Autowired
    private OciConfig ociConfig;

    String configurationFilePath = "~/.oci/config";
    String profile = "DEFAULT";

    @PostConstruct
    public void init(){
        System.out.println("Vault Client Initialized");
    }

    public static String getValueFromVault(String secretOcid, BasicAuthenticationDetailsProvider provider) throws IOException {
        SecretsClient secretsClient = new SecretsClient(provider);

        GetSecretBundleResponse getSecretBundleResponse;
        GetSecretBundleRequest getSecretBundleRequest = GetSecretBundleRequest
                .builder()
                .secretId(secretOcid)
                .stage(GetSecretBundleRequest.Stage.Current)
                .build();
        getSecretBundleResponse = secretsClient.getSecretBundle(getSecretBundleRequest);

        Base64SecretBundleContentDetails base64SecretBundleContentDetails =
                (Base64SecretBundleContentDetails) getSecretBundleResponse.getSecretBundle().getSecretBundleContent();
        byte[] secretValueDecoded = Base64.decode(base64SecretBundleContentDetails.getContent());

        secretsClient.close();
        return new String(secretValueDecoded);
    }

    public String getUsernameFromVault() throws IOException{
        final String userOcid = ociConfig.getDbUsernameOcid();
       ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();
        BasicAuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
        return new String(getValueFromVault(userOcid, provider));
    }

    public String getPasswordFromVault() throws IOException{
        final String passOcid = ociConfig.getDbPasswordOcid();
        ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();
        BasicAuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
        return new String(getValueFromVault(passOcid, provider));
    }    
}
