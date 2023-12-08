package com.starter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
	
	@Bean
	OpenAPI carDatabaseOpenAPI() {
		return new OpenAPI()
			.info(new Info()
			.title("E-Commerce REST API")
			.description("API documentation for the E-Commerce Platform. "
					+ "This API allows you to manage products, orders, "
					+ "and user information.")
			.version("1.0"));
	}	
}

