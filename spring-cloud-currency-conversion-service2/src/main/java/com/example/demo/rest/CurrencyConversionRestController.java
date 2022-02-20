package com.example.demo.rest;

import java.math.BigDecimal;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.CurrencyConversion;
import com.example.demo.proxy.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionRestController {
	
	Logger log = LoggerFactory.getLogger(CurrencyConversionRestController.class);
	
	@Autowired
	private CurrencyExchangeProxy currencyExchangeProxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,@PathVariable BigDecimal quantity) {
		
		log.info("To: " +  to);
		log.info("From: " + from);
		log.info("Quantity: " + quantity);
		
		
		HashMap<String,String> uriVarables = new HashMap<>();
		uriVarables.put("from",from);
		uriVarables.put("to",to);
		
		/*
		 * Wysylamy request do naszego mikroserwisu podajac link oraz typ w jaki ma zostac przekonwertowana odpowiedz,
		 * wysylamy rowniez Mape z wartosciami ktore chcemy podpisac pod @PathVariable z linku - klucze i nazwy musza sie pokrywac
		 * 
		 * !!! Obiekt w ktory chcemy przekonwertowac musi posiadac takie same zmienne jak w odpowiedzi ale nie musi byc ta sama klasa !!
		 * 
		 * Sprawdz nastepna metode !
		 */
		// WAZNE !!!
		// tutaj wywolujemy localhost - to nie zadziala w dockerze !!!
		// chyba ze zamienimy localhost na currency-exchange - nazwe serwisu z dockera
		// WAZNE !!!
		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate()
				.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class,uriVarables);
		
		CurrencyConversion currencyConversion = responseEntity.getBody();
		if(currencyConversion == null) {
			throw new RuntimeException("Conversion Exchange not found");
		}
		
		
		return new CurrencyConversion(currencyConversion.getId()
				,from,to,currencyConversion.getConversionMultiple(),quantity
				,quantity.multiply(currencyConversion.getConversionMultiple())
				,currencyConversion.getEnvironment());
		
	}
	
	/*
	 * To dopiero cudo !
	 * Stworzylem proxy CurrencyExchangeProxy przez ktore laczymy sie z innym mikroserwisem i mozemy wywolac jego metody restowe !
	 */
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to,@PathVariable BigDecimal quantity) {
		
		CurrencyConversion currencyConversion = currencyExchangeProxy.retriveExhangeValue(from, to);
		
		return new CurrencyConversion(currencyConversion.getId()
				,from,to,currencyConversion.getConversionMultiple(),quantity
				,quantity.multiply(currencyConversion.getConversionMultiple())
				,currencyConversion.getEnvironment());
		
		
		
	}

}
