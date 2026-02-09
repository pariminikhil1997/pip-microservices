package com.java.pip.dto;

import java.util.Set;

public record UserResponseDTO (
	Long id,
	String name,
	String email,
	Set<String> roles) {

}