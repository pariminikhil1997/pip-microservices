package com.java.pip.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ProductClientConfig {
	
	@Bean
	WebClient productWebClient(WebClient.Builder builder,
			                   @Value("${product.service.url}") String baseUrl) {
	
		 return builder.baseUrl(baseUrl).build();
	}
}