package com.example.st_mission.dto.auth;

import java.util.List;

public record JWTResponseDTO(
        Long id,
        String name,
        String email,
        List<String> roles,
        String type,
        String token
) {
}
