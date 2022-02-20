package com.example.demo.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.CurrencyConversion;

/*
 * Podajemy nazwe serwisu do ktorego chcemy wysylac zapytania,
 * musi zgadzac sie z nazwa applikacji w application.yml tego serwisu
 * Podajemy rowniez url tego serwisu, bez endpointow
 */
//@FeignClient(name="currency-exchange", url="localhost:8000")


/*
 * Jesli mamy Eureka Discovery Client to nie musimy podawac url,
 * Feign sam znajdzie Eureke i zapyta o adres a Eureka uzyje LoadBalencera
 */
@FeignClient(name="currency-exchange")
public interface CurrencyExchangeProxy {

	/*
	 * Najlepiej jest skopiowac cala metode z klasy do ktorej chcemy wyslac zapytanie, w tym wypadku z CurrencyExchange
	 * Podajemy objekt do ktorego maja zostac zwrocone dane 
	 * 
	 * --- objekt ten musi posiadac te same pola co objekt zwracany(te ktore nas interesuja)---
	 */
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retriveExhangeValue(@PathVariable String from,@PathVariable String to);
}
