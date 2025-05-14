package com.example.st_mission.mapper;


import com.example.st_mission.dto.submission.SubmissionDTO;
import com.example.st_mission.model.SubmissionEntity;

public class SubmissionMapper {

    public static SubmissionDTO toDTO(SubmissionEntity submission) {
        return new SubmissionDTO(
                submission.getId(),
                submission.getTask().getId(),
                submission.getUser().getId(),
                submission.getContent(),
                submission.getStatus().name(),
                submission.getGrade(),
                submission.getSubmittedAt()
        );
    }

}
