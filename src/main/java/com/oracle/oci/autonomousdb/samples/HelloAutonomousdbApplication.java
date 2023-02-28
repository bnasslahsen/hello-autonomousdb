package com.oracle.oci.autonomousdb.samples;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.oracle.oci.autonomousdb.samples.common.config.VaultClient;

@SpringBootApplication
@ComponentScan(basePackages = "com.oracle.oci.autonomousdb.samples.common.config")
@ComponentScan(basePackages = "com.oracle.oci.autonomousdb.samples.presentation.rest")
@EnableAutoConfiguration
public class HelloAutonomousdbApplication {
	public static void main(String[] args) {
		SpringApplication.run(HelloAutonomousdbApplication.class, args);
	}

}
