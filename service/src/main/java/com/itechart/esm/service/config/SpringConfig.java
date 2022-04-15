package com.itechart.esm.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.itechart.esm.service")
public class SpringConfig {

	private final ApplicationContext applicationContext;

	@Autowired
	public SpringConfig(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
