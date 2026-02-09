package com.java.pip.client;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.java.pip.dto.ProductResponseDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductClient {
	
	private final RestTemplate restTemplate;
	
	private static final String PRODUCT_SERVICE = "http://product-service:8081/api/products/";
	
	@CircuitBreaker(name = "productService", fallbackMethod = "fallbackProduct")
	@Retry(name = "productService")
	@TimeLimiter(name = "productService")
	public CompletableFuture<ProductResponseDTO> getProduct(Long productId) {
		
		log.debug("Calling product-service productId={}", productId);
		
		return CompletableFuture.supplyAsync(() ->
        restTemplate.getForObject(PRODUCT_SERVICE + productId,ProductResponseDTO.class));
    }
	
	public CompletableFuture<ProductResponseDTO> fallbackProduct(Long productId, Throwable ex) {
        log.error("Fallback triggered for productId={} reason={}",productId, ex.getMessage());

        return CompletableFuture.completedFuture(new ProductResponseDTO(
                productId,
                "UNKNOWN_PRODUCT",
                0.0));
    }
}