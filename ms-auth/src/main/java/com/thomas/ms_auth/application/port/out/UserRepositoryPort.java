package com.thomas.ms_auth.application.port.out;

import com.thomas.ms_auth.domain.User;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
