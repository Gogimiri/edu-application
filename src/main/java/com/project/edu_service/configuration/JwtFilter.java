package com.project.edu_service.configuration;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.edu_service.security.JwtUtil;
import com.project.edu_service.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        log.info("Authorization header: {}", authHeader); //
        if(!StringUtils.isEmpty(authHeader) && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            log.info("Extracted JWT: {}", jwt);

            if(jwt.isBlank()) {
                log.warn("JWT is empty.");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,"your jwt is empty bro");
            } else  {

                try {

                    String username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                    log.info("Valid JWT for user: {}", username);
                    UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                                    userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        log.info("Authentication set for user: {}", username);
                    }
                } catch (JWTVerificationException e) {
                    log.error("JWT verification failed: {}", e.getMessage());
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "no no no bro, something problem with your jwt");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
