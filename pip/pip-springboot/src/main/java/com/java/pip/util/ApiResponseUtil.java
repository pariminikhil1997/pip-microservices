package com.java.pip.util;

public record ApiResponseUtil<T>(
		String status,
		T data) 
{}