package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/*
 * !!! WAZNE !!!
 * 
 * Kolejna wazna addnotacja !!!
 */

@SpringBootApplication
@EnableEurekaServer	
public class SpringCloudNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudNamingServerApplication.class, args);
	}

}
