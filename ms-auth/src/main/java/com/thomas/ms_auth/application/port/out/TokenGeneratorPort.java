package com.thomas.ms_auth.application.port.out;

import com.thomas.ms_auth.domain.User;

public interface TokenGeneratorPort {
    String generateToken(User user);
    boolean validateToken(String token);
    String extractEmail(String token);
    Long getExpirationInSeconds();
}
