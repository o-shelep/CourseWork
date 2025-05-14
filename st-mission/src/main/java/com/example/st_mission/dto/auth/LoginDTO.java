package com.example.st_mission.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginDTO(
        @Schema(description = "User email", example = "user@example.com")
        String email,

        @Schema(description = "Password", example = "Secure123$")
        String password
) {
}
