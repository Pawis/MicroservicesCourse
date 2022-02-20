package com.example.demo.rest;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CurrencyExchange;
import com.example.demo.repo.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {
	
	private static final Logger log = LoggerFactory.getLogger(CurrencyExchangeController.class);

	
	// Przez ta zmienna mzoemy zadawac pytanie do springa typu : na jakim porcie aktualnie pracujemy?
	@Autowired
	private Environment env;
	
	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepo;


	// Uzywamy BigDecimal jesli chcemy uzyskac bardzo duza dokladnosc 

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retriveExhangeValue(@PathVariable String from,@PathVariable String to) {

		log.info("retriveExhangeValue called with {} to {}",from,to);

		
		CurrencyExchange currencyExchange = currencyExchangeRepo.findByFromAndTo(from, to)
				.orElseThrow(() -> new RuntimeException("Unable to find data for " + from + "to " + to));

		log.info("ConversionMultiple: " + currencyExchange.getConversionMultiple());
		// putamy spring o port na ktorym pracujemy
		String port = env.getProperty("local.server.port");
		// ustawiamy ten port do naszje zmiennej w modelu
		currencyExchange.setEnvironment(port);

		return currencyExchange;

	}


}
