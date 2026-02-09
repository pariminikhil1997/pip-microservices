package com.java.pip.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductRequestDTO (
	
	@NotBlank(message = "Name is required")
	String name,
	
	@Positive(message = "Price must be positive")
    Double price ) 
{}
