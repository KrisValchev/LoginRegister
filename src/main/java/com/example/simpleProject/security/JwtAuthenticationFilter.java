package com.example.simpleProject.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collections;

//This class extends OncePerRequestFilter, which ensures the filter logic runs once per HTTP request (not multiple times during the same request).
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //secret key used to validate JWT signature, matching the one when creating the token
    private final String jwtSecret = "your-256-bit-secret-key-goes-here"; // Replace with your actual secret

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//Extracts the JWT from the Authorization header.
        String jwt = extractTokenFromRequest(request);
//Checks if the JWT exists and is valid
        if (StringUtils.hasText(jwt) && validateToken(jwt)) {
            //Extracts the user ID from the token
            String userId = extractUserId(jwt);
//Creates an authentication token with the user ID as the principal. You could also set roles/authorities if needed.
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
//Adds request-specific details to the authentication object
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set authentication context
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
               // puts the authenticated user in Spring Security's contex
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);

    }
//Extracts the token from the Authorization header by removing the Bearer prefix.
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // remove "Bearer " prefix
        }
        return null;
    }
//Tries to parse the JWT. If it throws an exception
    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            System.out.println("JWT Validation failed: " + ex.getMessage());
            return false; // invalid token
        }
    }
//Parses the JWT and gets the subject from its claims
    private String extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // assuming user ID is in subject
    }
}

