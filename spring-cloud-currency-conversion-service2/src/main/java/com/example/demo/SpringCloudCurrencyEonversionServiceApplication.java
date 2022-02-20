package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;



/*
 * !!! WAZNE !!!
 * Wazna addnotacja pamietac o niej !!!
 */
@SpringBootApplication
@EnableFeignClients
public class SpringCloudCurrencyEonversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudCurrencyEonversionServiceApplication.class, args);
	}

}
