package com.java.pip.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.pip.dto.LoginRequestDTO;
import com.java.pip.dto.LoginResponseDTO;
import com.java.pip.entity.User;
import com.java.pip.exception.AuthenticationException;
import com.java.pip.repository.UserRepository;
import com.java.pip.security.JwtTokenProvider;
import com.java.pip.util.ApiResponseUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/login")
	public ApiResponseUtil<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto){
		User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> 
		                                     new AuthenticationException("Invalid credentials"));
		
		if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			
			throw new AuthenticationException("Invalid credentials");
		}
		
		String token = jwtTokenProvider.generateToken(user);
		
		return new ApiResponseUtil<>("SUCCESS", new LoginResponseDTO(token));
	}
}