package com.thomas.ms_auth.infrastructure.adapter.out.security;

import com.thomas.ms_auth.application.port.out.TokenGeneratorPort;
import com.thomas.ms_auth.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
@Component
public class JwtTokenAdapter implements TokenGeneratorPort {
    private final String secret;
    private final String issuer;
    private final long expirationSeconds;

    public JwtTokenAdapter(
            @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.issuer}") String issuer,
            @Value("${spring.jwt.expiration-seconds}") long expirationSeconds
    ) {
        this.secret = secret;
        this.issuer = issuer;
        this.expirationSeconds = expirationSeconds;
    }

    @Override
    public String generateToken(User user) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(getExpirationInSeconds());
        return Jwts.builder()
                .issuer(issuer)
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expirationSeconds)))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try{
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .requireIssuer(issuer)
                    .build()
                    .parseSignedClaims(token);
            return true;

        }catch (Exception e){
            return false;
        }
    }

    @Override
    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public Long getExpirationInSeconds() {
        return expirationSeconds;
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
