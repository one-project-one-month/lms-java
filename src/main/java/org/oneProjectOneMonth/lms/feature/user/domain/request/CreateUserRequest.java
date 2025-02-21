package org.oneProjectOneMonth.lms.feature.user.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;



import java.time.LocalDate;
import java.util.Set;

/**
 * Data Transfer Object for creating a new user.
 */
public record CreateUserRequest(

        @NotNull(message = "Name is required")
        String name,
        @NotNull(message = "Username is required")
        String username,
        @Email
        @NotNull(message = "Email is required")
        String email,
        String password,
        String roles,
        @NotNull(message = "Phone number is required")
        String phone,
        @NotNull(message = "Date of birth is required")
        @Past(message = "Birthday must be in the past")
        LocalDate dob,
        String nrc,
        String eduBackground,
        String address,
        String profilePhoto
) {
}
