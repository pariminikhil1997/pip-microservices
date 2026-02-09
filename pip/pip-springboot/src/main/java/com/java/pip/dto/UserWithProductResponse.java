package com.java.pip.dto;

public record UserWithProductResponse(Long userId,
		                              String name,
		                              String email,
		                              ProductResponseDTO product) {}