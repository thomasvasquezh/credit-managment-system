package com.thomas.ms_auth.domain;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime createdAt;

    public String fullName() {
        String first = firstName == null ? "" : firstName.trim();
        String last = lastName == null ? "" : lastName.trim();
        return (first + " " + last).trim();
    }

    public static User create(
            String firstName,
            String lastName,
            String email,
            String encodedPassword,
            UserRole role,
            UserStatus status,
            LocalDateTime createdAt
    ){
        return new User(
                null,
                firstName,
                lastName,
                email,
                encodedPassword,
                role,
                status,
                createdAt
        );
    }

    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }
}
