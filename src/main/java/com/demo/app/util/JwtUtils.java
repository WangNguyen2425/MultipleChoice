package com.demo.app.util;

import com.demo.app.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;


@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecretKey;

    public String generateJwtToken(Authentication auth){
        User principal = (User) auth.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1, DAYS)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String jwt, String username){
        return getSubject(jwt).equals(username) && !isTokenExpired(jwt);

    }

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    private boolean isTokenExpired(String jwt){
        Date today = Date.from(Instant.now());
        return getClaims(jwt).getExpiration().before(today);
    }

    private Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
