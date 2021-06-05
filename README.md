# hello-autonomousdb

# What is Hello Autonomous DB project?
The demo shows how you can integrate a spring-boot cloud native application with Oracle autonomous DB.
You will be able to run this application from Your IDE or you can deploy it in Oracle Cloud Infrastructure.

# Protecting your database credentials with OCI Vault 

OCI Vault is a Centralized and customer controlled key management.
It is: 
- natively integrated to many OCI services – Autonomous, Exadata, Object Storage and others
- Fully managed
- highly available service
- Support regulatory compliance: Meets PCI DSS and FIPS 140-2 Level 3 standard for cryptographic processing

Hardcoding the database credentials in the configuration files might be acceptable for less critical environements like local developer station.
On the other hand, using a Secret Manager is number 1 security best practice for protecting any sensitive information (example, DB credentials for a production environment):
- Store and retrieve any sensitive information
- Encrypted using keys from Vault

Using Secret management in OCI Vault, is completely free. 
We will see it's easy to integrate with spring-boot application using oci-sdk.

# Running the application in your I.D.E

## Pre-requisites
An autonomous Database should be already Provisioned on Oracle cloud.
You can follow these steps for more details: //TODO.
## Application configuration
Simply replace the following properties with your own configuration:
## Application execution
Launch the application, using the class: HelloAutonomousdbApplication
Test the access to the application: http://localhost:8080/.


# Running the application in your Oracle Cloud Tenant
## Pre-requisites
- kubectl
- docker CLI
- Terraform 0.12.16+ is Installed
- OCI CLI Installed
- Create a compartments: `Test`
- maven 3

## Getting your tenant configuration
- You have setup the link:https://docs.cloud.oracle.com/iaas/Content/API/Concepts/apisigningkey.htm[required keys]
- You know the link:https://docs.cloud.oracle.com/iaas/Content/API/Concepts/apisigningkey.htm#five[required OCIDs]

## Provisioning the infrastructure with Terraform

After following the configuration steps:
```
terraform init
terraform plan
terraform apply
```

## Building the application 

### Option 1: Building Executable JAR with Maven
To create an `executable jar`, simply run:

```sh
mvn clean package
```

### Option 1: Testing Executable JAR locally

```sh
 ava -jar target/hello-autonomousdb-0.0.1-SNAPSHOT.jar --spring.config.location=/Users/bnasslah/Documents/workspace/hello-autonomousdb/src/main/resources/
```

### Option 2: Building a non-native OCI Images
We will create an OCI image from an executable jar file using Cloud Native Buildpacks (CNB).
Images can be built using the build-image goal.

To create a non-native OCI docker image, simply run.

```sh
mvn clean spring-boot:build-image
```

### Option 3: Building native image with GraalVM
To create a `native image`, the project rely on spring-native project and buildpacks.
Run the following command

```sh
mvn -Pnative-image clean spring-boot:build-image
```


## Configuring the application secrets

This file defines the microprofile standard. It also has the definition of the data sources that will be injected. 
The universal connection pool takes the JDBC URL and DB credentials to connect and inject the datasource. 
The file has default values which will be overwritten by the environment variables that are passed in.

The dbpassword environment variable is read and set as the password unless and vault OCID is provided.

Let’s also look at the microservice source file OrderResource.java.

### Deploying the

Secrets PropertySource
Kubernetes has the notion of Secrets for storing sensitive data such as passwords, OAuth tokens, and so on. 
This project provides integration with Secrets to make secrets accessible by Spring Boot applications.
You can explicitly enable or disable This feature by setting the spring.cloud.kubernetes.secrets.enabled property.



### Deploying the


The Auth Token must first be manually created and stored in OCI Secret in Vault.
It will subsequently be used to create a Kubernetes secret, which can then be used as an imagePullSecrets in a deployment. 

Now let us say that the Kubernetes encryption of the etcd and RBAC are not enough for your use. You might want to switch to dedicated technologies. 

In addition to Kubernetes secrets,

 you can also retrieve secrets from OCI Vault using instance principal (whole node pool) or user principal. We don’t support resource principal yet.

## Deploying the application to Kubernetes using buildpacks
```sh
cd 
./deploy-app.sh
```

scp -r opc@130.61.203.164:/opt/apache-maven-3.6.3.zip .


scp -r opc@129.159.249.139:/home/opc/hello-autonomousdb .
rm -rf src/test/resources/application.yml

mvn clean compile test -Dspring.profiles.active=oci

mvn clean package -Dspring.profiles.active=oci

export JAVA_HOME=/home/opc/graalvm-ee-java8-21.1.0
export PATH=/home/opc/apache-maven-3.8.1/bin:$PATH
export PATH=$JAVA_HOME/bin:$PATH

export JAVA_HOME=/Library/Java/JavaVirtualMachines/graalvm-ee-java8-21.1.0/Contents/Home

java -Dspring.profiles.active=oci -Dspring.config.location=src/main/resources/ -agentlib:native-image-agent=access-filter-file=src/test/resources/access-filter.json,config-output-dir=src/main/resources/META-INF/native-image/ -jar target/hello-autonomousdb-0.0.1-SNAPSHOT.jar


## Deploying the application to Kubernetes using buildpacks with native images


mvn -Pnative-image clean package -Dspring.profiles.active=oci

mvn -Pnative-image clean spring-boot:build-image


mvn -Pnative-image clean package jib:build

docker build -t springdoc-openapi-spring-boot-2-webflux -f springdoc-openapi-spring-boot-2-webflux/Dockerfile .

scp -r /Users/bnasslah/Documents/workspace/hello-autonomousdb opc@129.159.201.215:/home/opc

