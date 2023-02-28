/**

Main class for the Hello Autonomous Database application.
This application demonstrates how to use Spring Boot and Oracle Autonomous Database to connect to a database, retrieve data, and display it in a RESTful web service, while retrieving
credentials from an OCI Vault.
*/

package com.oracle.oci.autonomousdb.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main method to start the application.
 * @param args command line arguments
 */

@SpringBootApplication
@ComponentScan(basePackages = "com.oracle.oci.autonomousdb.samples.common.config")
@ComponentScan(basePackages = "com.oracle.oci.autonomousdb.samples.presentation.rest")
@EnableAutoConfiguration
public class HelloAutonomousdbApplication {
	public static void main(String[] args) {
		SpringApplication.run(HelloAutonomousdbApplication.class, args);
	}

}
