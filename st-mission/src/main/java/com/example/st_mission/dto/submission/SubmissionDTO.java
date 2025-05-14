package com.example.st_mission.dto.submission;

import java.time.LocalDateTime;

public record SubmissionDTO(
        Long id,
        Long taskId,
        Long userId,
        String content,
        String status,
        int grade,
        LocalDateTime submittedAt
) {}

