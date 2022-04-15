package com.itechart.esm.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan({"com.itechart.esm", "com.itechart.esm.service.config", "com.itechart.esm.repository.jdbc_template" +
		".config"})
public class SpringConfig implements WebMvcConfigurer {
	private final ApplicationContext applicationContext;

	@Autowired
	public SpringConfig(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter());
	}
}
