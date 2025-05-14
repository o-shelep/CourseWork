package com.example.st_mission.dto.task;

import java.time.LocalDateTime;

public record TaskDTO(
        Long id,
        String title,
        String description,
        int points,
        LocalDateTime deadline,
        String createdByName
) {}

