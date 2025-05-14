package com.example.st_mission.dto;


import com.example.st_mission.model.ERole;
import jakarta.validation.constraints.NotNull;

public record ChangeRoleDTO(
        @NotNull(message = "Role is required")
        ERole role
) {
}
