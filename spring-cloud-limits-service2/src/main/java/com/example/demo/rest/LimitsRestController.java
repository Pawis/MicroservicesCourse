package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.ConfigurationProps;
import com.example.demo.model.Limits;

@RestController
public class LimitsRestController {

	@Autowired
	private ConfigurationProps configuration;
	
	@GetMapping("/limits")
	public Limits getLimits() {
		return new Limits(configuration.getMinimum(),configuration.getMaximum());
	}
}
