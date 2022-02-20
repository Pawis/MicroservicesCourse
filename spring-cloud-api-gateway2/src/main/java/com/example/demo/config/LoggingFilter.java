package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {

	private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);


	/*
	 * Mozemy dodac logowanie przed dokonczeniem rzadania ktore dostalismy 
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		logger.info("Path of the request recived: " + exchange.getRequest().getPath());
		
		// kontynulujemy przekierowanie 
		return chain.filter(exchange);
	}

}
