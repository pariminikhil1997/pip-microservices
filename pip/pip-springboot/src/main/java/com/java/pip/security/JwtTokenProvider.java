package com.java.pip.security;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.java.pip.entity.Role;
import com.java.pip.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	// minimum 32 chars for HS256
	private static final String SECRET = "my-super-secret-key-my-super-secret-key-123456";
	
	private static final long EXPIRATION_TIME = 60*60*1000;
	

	public String generateToken(User user) {
		
		List<String> roles = user.getRoles().stream().map(Role::getName).toList();
		
		return Jwts.builder().subject(user.getEmail())
				.claim("roles", roles)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
				.compact();
	}
	
	public Claims extractClaims(String token) {
		
		return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public String getEmail(String token) {
		return extractClaims(token).getSubject();
	}
}