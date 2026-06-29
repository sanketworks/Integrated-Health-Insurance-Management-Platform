package com.nt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class ApplicationRegistrationServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(ApplicationRegistrationServiceApplication.class, args);
	}

}
