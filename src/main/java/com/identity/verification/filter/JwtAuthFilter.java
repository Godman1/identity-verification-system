package com.identity.verification.filter;

import com.identity.verification.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService; // Injected JwtService
    private final UserDetailsService userDetailsService; // Injected UserDetailsService

    // Constructor-based injection of dependencies
    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String username;

        // Check if Authorization header exists and if it starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Proceed to next filter
            return;
        }

        // Extract token and username from the authorization header
        token = authHeader.substring(7); // Remove "Bearer " prefix
        username = jwtService.extractUsername(token); // Get username from token

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the database using UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate token
            if (jwtService.validateToken(token, userDetails)) {
                // If valid, create an Authentication object
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set the details and authenticate the request
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken); // Set the authentication in the security context
            }
        }

        filterChain.doFilter(request, response); // Proceed to the next filter
    }
}