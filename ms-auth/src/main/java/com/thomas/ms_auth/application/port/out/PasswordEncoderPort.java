package com.thomas.ms_auth.application.port.out;

public interface PasswordEncoderPort {
    String encode (String rawPassword);
    boolean matches(String rawPassword, String encondedPassword);
}
