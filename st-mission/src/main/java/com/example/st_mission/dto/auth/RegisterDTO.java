package com.example.st_mission.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDTO(

        @Schema(description = "Full name of the user", example = "userName")
        @NotBlank(message = "Name cannot be empty")
        String name,

        @Schema(description = "Valid email address", example = "user@example.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        @Size(max = 50, message = "Email must be less than 50 characters")
        String email,

        @Schema(
                description = "Secure password (min 6 chars, 1 upper, 1 lower, 1 digit)",
                example = "Secure123$"
        )
        @NotBlank(message = "Password is required")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
                message = "Password must contain at least one uppercase letter," +
                        " one lowercase letter and one number")
        @Size(min = 6, message = "The password must contain at least 6 characters")
        String password
) {
}
