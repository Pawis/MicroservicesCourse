package com.example.demo.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

	/*
	 * Co tutaj sie dzieje:
	 * ApiGateway pozwala nam stworzyc jeden link do ktorego beda kierowane wywolania zamiast wielu linkow do kazdego serwisu z osobna
	 * Polega to na tym ze dla sieci publicznej udostepniamy dostep do tego serwisu dzieki czemu mozemy przekierowac kazde wywolanie 
	 * do wybranego mikroserwisu 
	 * Mozemy rowniez ustalic zasady ktorymi mozemy sie kierowac podczas przekierowania rzadania np. mozemy sprawdzic czy w headerze jest jakas wartosc
	 * albo czy link posiada konkretny parametr przez uzycie Predictables czyli poprostu czegos co wytwarza boolean.
	 */
	
	@Bean
	public RouteLocator gatewayTouter(RouteLocatorBuilder routeLocationBuilder) {
		
		return routeLocationBuilder
				
				.routes()
				
				// Prosty przyklad
				.route(p -> p.path("/get") // Jesli rzadanie jest zakonczone /get (localhost:8765/get) to...
						.filters(f -> f // mozemy je przefiltrowac...
								.addRequestHeader("MyHeader", "MyURI") // mozemy dodac header...
								.addRequestParameter("Param", "MyValue")) // mozemy dodac parametr...
						.uri("http://google.com")) // po czym odsylamy do wybranego url naszego mikroserwisu gdzie 
				                                   //  /get zostaje dodane na koniec - https://www.google.com/get?Param=MyValue
				/*
				 * W tym przypadku jesli rzadanie konczy sie /currency-exchange/** to znaczy ze przyjmujemy kazda wartosc 
				 * z currency-exchange/ + cookolwiek po tym(** wildcard) i odsylamy to do lb://currency-exchange
				 * lb przed linkiem oznacza ze chcemy uzyc LoadBalancera jesli mamy pare mikroserwisow 
				 * czyli wyjdzie np /currency-conversion-feign/from/USD/to/INR/quantity/10
				 * from/USD/to/INR/quantity/10 przenosimy do lb://currency-conversion/from/USD/to/INR/quantity/10"
				 */
				.route(p -> p.path("/currency-exchange/**").uri("lb://currency-exchange")) 
				.route(p -> p.path("/currency-conversion-feign/**").uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion/**").uri("lb://currency-conversion"))
				
				/*
				 * Mozemy tez zmienic czesc po /currency-conversion-new/ uzywajac rewritePath
				 * zamieniamy wildcard na (?<segment>.*) przy uzyciu Pttern i przenosimy go do
				 * /currency-conversion-feign/${segment} nazego nowego linku
				 * po czym wywolujemy nasz serwis na lb://currency-conversion + Pattern
				 * 
				 * Czyli 
				 * localhost:8765/currency-conversion-new/from/USD/to/INR/quantity/10  --> localhost:8765/currency-conversion-feign/from/USD/to/INR/quantity/10 
				 *  --> lb://currency-conversion/rom/USD/to/INR/quantity/10 
				 */
				.route(p -> p.path("/currency-conversion-new/**")
						.filters(f -> f.rewritePath(
								"/currency-conversion-new/(?<segment>.*)",
								"/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion"))
				.build();
	}
}
