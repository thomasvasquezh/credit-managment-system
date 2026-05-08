package com.thomas.ms_auth.infrastructure.config;

import com.thomas.ms_auth.application.port.in.RegisterUserUseCase;
import com.thomas.ms_auth.application.port.out.PasswordEncoderPort;
import com.thomas.ms_auth.application.port.out.UserRepositoryPort;
import com.thomas.ms_auth.application.service.RegisterUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {
    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort
    ) {
        return new RegisterUserService(userRepositoryPort, passwordEncoderPort);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
