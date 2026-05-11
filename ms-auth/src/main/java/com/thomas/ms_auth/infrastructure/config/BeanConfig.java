package com.thomas.ms_auth.infrastructure.config;

import com.thomas.ms_auth.application.port.in.LoginUseCase;
import com.thomas.ms_auth.application.port.in.RegisterUserUseCase;
import com.thomas.ms_auth.application.port.out.PasswordEncoderPort;
import com.thomas.ms_auth.application.port.out.TokenGeneratorPort;
import com.thomas.ms_auth.application.port.out.UserRepositoryPort;
import com.thomas.ms_auth.application.service.LoginService;
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
    public LoginUseCase loginUseCase(
            UserRepositoryPort userRepositoryPort,
            TokenGeneratorPort tokenGeneratorPort,
            PasswordEncoderPort passwordEncoderPort
    ){
        return new LoginService(userRepositoryPort, tokenGeneratorPort, passwordEncoderPort );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
