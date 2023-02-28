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

/**
 * A utility class for accessing secrets stored in Oracle Cloud Infrastructure Vault service. It requires to have the file of an already whitelisted user with proper privileges into oci
 * merged in file under ~/.oci/config This is the simplest implementation and won't use instance principals. Make sure to copy this file in the pods
 */
@Component
public class VaultClient {

    @Autowired
    private Environment env;

    @Autowired
    private OciConfig ociConfig;

    String configurationFilePath = "~/.oci/config";
    String profile = "DEFAULT";

    /**
     * Initializes the Vault client instance. Prints out a message in standard out to show Client Initialization
     */
    @PostConstruct
    public void init(){
        System.out.println("Vault Client Initialized");
    }

    /**
     * Retrieves the value of the secret with the given OCID using the specified authentication provider.
     *
     * @param secretOcid The OCID of the secret to retrieve.
     * @param provider The authentication provider to use for accessing the Vault service.
     * @return The value of the secret.
     * @throws IOException If an error occurs while retrieving the secret.
     */
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

    /**
     * Retrieves the username for the database connection from the Vault service.
     *
     * @return The username for the database connection.
     * @throws IOException If an error occurs while retrieving the username.
     */
    public String getUsernameFromVault() throws IOException{
        final String userOcid = ociConfig.getDbUsernameOcid();
        ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();
        BasicAuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
        return new String(getValueFromVault(userOcid, provider));
    }

    /**
     * Retrieves the password for the database connection from the Vault service.
     *
     * @return The password for the database connection.
     * @throws IOException If an error occurs while retrieving the password.
     */
    public String getPasswordFromVault() throws IOException{
        final String passOcid = ociConfig.getDbPasswordOcid();
        ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();
        BasicAuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
        return new String(getValueFromVault(passOcid, provider));
    }    
}
