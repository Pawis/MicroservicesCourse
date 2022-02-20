package com.example.demo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;

@RestController
public class CircuitBreakerController {

	private static final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	/*
	 * @Retry
	 * Podajemy nazwe z pliku application.yml, moozemy podac nazwe metody ktora zostanie 
	 * wywolana jesli ilosc powtorzen przekroczy limit z pliku application.yml
	 * 
	 * @CircuitBreaker
	 * Jesli ilosc rzadan przekroczy prog ustawiony przez resilience to nastepne rzadania zostaja odrazu odeslane 
	 * bez wchodzenia w metode, po pewnym czasie CircuitBreaker znow otworzy metode zeby sprawdzic czy 
	 * da sie juz jej uzywac jesli nie to znowu ja zamknie
	 * 
	 * $ watch -n 0.1  curl localhost:8000/sample-api
	 * Dobry spodob na duza ilosc requestow
	 * 
	 * @RateLimiter
	 * Mozemy okreslic ile requestow wpuszczamy w okreslonym czasie np 2 requesty w 10 sekund reszta dostanie wyjatek
	 * 
	 * @Bulkhead
	 * Okreslamy ilosc requestow Concurrently np 10 jaka wpuszczamy jednoczesnie
	 */
	@GetMapping("/sample-api")
	// @Retry(name = "sample-api",fallbackMethod = "hardCodedResponse") 
	// @CircuitBreaker(name = "sample-api",fallbackMethod = "hardCodedResponse") 
	// @RateLimiter(name = "default")
	@Bulkhead(name="default")
	public String sampleApi() {
		
		logger.info("SampleApi call; recived");
		/*
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/dummy", String.class);
		
		return forEntity.getBody();
		*/
		return "sample-apo";
	}
	
	/*
	 * Tworzymy metode ktora zostanie wywolana jesli osiagniemy maximum retry naszej glownej metody
	 * Musimy podac Exception w parametrze, jesli stworzymy kilka metod z ta sama nazwazostanie wybrana ta 
	 * ktorej wyjatek zostal wywolany np.
	 * Jesli glowna metoda wywali AccountNotFound to uzyje metody z argumentem AccountNotFound
	 * przez ktora bedziemy mogli dpoodac specjalna odpowiedz na ten wyjatek
	 * 
	 */
	public String hardCodedResponse(Exception ex) {
		return "fallback-response";
	}
}
