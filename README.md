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

## Building the application 

### Option 1: Building Executable JAR with Maven
To create an `executable jar`, simply run:

```sh
mvn clean package
```

### Option 1: Testing Executable JAR locally

```sh
 java -jar target/hello-autonomousdb-0.0.1-SNAPSHOT.jar --spring.config.location=/Users/bnasslah/Documents/workspace/hello-autonomousdb/src/main/resources/
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
