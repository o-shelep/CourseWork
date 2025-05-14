package com.example.st_mission.dto.user;



import com.example.st_mission.model.SubmissionEntity;

import java.util.List;

public record UserDTO(
         Long id,
         String name,
         String email,
         List<String> roles,
         int level,
         int points,
         List<String> achievements,
         List<SubmissionEntity> submissions
) {
}
