package com.project.edu_service.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.edu_service.entityes.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    @Value("{jwt.secret}")
    private String secret;

    public String generateToken(String username, Role roles) {
        Date expirationTime = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("username", username)
                .withClaim("roles", String.valueOf(roles))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withIssuer("dmitry")
                .withExpiresAt(expirationTime)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier =  JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("dmitry")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }

    public boolean isTokenExpired(String token) {
        return JWT.decode(token).getExpiresAt().before(new Date());
    }

    public List<String> extractRoles(String token) {
        return JWT.decode(token).getClaim("roles").asList(String.class);
    }
}
