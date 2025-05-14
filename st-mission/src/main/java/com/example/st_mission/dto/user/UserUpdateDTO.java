package com.example.st_mission.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(

        String name,

        @Email(message = "Email is invalid")
        @Size(max = 50, message = "Email must be less than 50 characters")
        String email,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
                message = "Password must contain at least one uppercase letter," +
                        " one lowercase letter and one number")
        @Size(min = 6, message = "The password must contain at least 6 characters")
        String password

) {
}
