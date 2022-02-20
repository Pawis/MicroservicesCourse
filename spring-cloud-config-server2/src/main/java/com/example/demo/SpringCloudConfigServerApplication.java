package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
/*
 * !!!! WAZNE !!!!
 * Adnotacja oznaczajaca ze ten projekt jest Cloud Serverem
 * Do servera laczymy sie przez localhost:8888/<nazwa pliku z wlasciwosciami w git repo np. limits-service>/default
 * 
 * Ogolnie musimy stworzyc git repo do ktorego ten server moze sie polaczyc 
 */
@EnableConfigServer
@SpringBootApplication
public class SpringCloudConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigServerApplication.class, args);
	}

}
