package com.example.st_mission.dto.task;

import java.time.LocalDateTime;

public record TaskCreateDTO(
        String title,
        String description,
        int points,
        LocalDateTime deadline
) {
}
