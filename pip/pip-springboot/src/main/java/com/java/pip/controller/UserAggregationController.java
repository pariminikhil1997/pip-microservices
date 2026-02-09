package com.java.pip.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.pip.dto.UserWithProductResponse;
import com.java.pip.service.UserAggregationService;
import com.java.pip.util.ApiResponseUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserAggregationController {
	
	private final UserAggregationService userAggregationService;
	
	@PreAuthorize("permitAll()")
	@GetMapping("/{userId}/products/{productId}")
	public ApiResponseUtil<UserWithProductResponse> getUserWithProduct(
            @PathVariable Long userId,
            @PathVariable Long productId) {

        return new ApiResponseUtil<>("SUCCESS",
        		userAggregationService.getUserWithProduct(userId, productId));
    }

}
