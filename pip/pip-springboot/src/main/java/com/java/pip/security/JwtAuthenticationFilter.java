package com.java.pip.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtTokenProvider tokenProvider;
    
    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
    	
    	this.tokenProvider = tokenProvider;
    }
	
	@Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            
        	String token = header.substring(7);
        	Claims claims;
        	try {
        		claims = tokenProvider.extractClaims(token);
        	} catch(JwtException e) {
        		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        		return;
        	}
            String email = claims.getSubject();
            
            @SuppressWarnings("unchecked")
            List<String> roles = claims.get("roles", List.class);
            
            var authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            
            UsernamePasswordAuthenticationToken authentication = 
            		new UsernamePasswordAuthenticationToken(email, null, authorities);
            
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}