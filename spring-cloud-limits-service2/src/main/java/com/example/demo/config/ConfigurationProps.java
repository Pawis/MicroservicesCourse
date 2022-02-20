package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;

/*
 *  Sposob na uzywanie wlasciosci z pliku application.properties, zeby ich nie hard kodowac
 */

@Component
@ConfigurationProperties("limits-service")
public class ConfigurationProps {

	private int minimum;
	private int maximum;

	public int getMinimum() {
		return minimum;
	}
	public int getMaximum() {
		return maximum;
	}
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}




	
}
