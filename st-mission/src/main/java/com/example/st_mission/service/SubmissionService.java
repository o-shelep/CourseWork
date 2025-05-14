package com.example.st_mission.service;


import com.example.st_mission.dto.submission.SubmissionDTO;
import com.example.st_mission.mapper.SubmissionMapper;
import com.example.st_mission.model.SubmissionEntity;
import com.example.st_mission.model.TaskEntity;
import com.example.st_mission.model.UserEntity;
import com.example.st_mission.repo.SubmissionRepository;
import com.example.st_mission.repo.TaskRepository;
import com.example.st_mission.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public SubmissionService(SubmissionRepository submissionRepository,
                             TaskRepository taskRepository,
                             UserRepository userRepository) {
        this.submissionRepository = submissionRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public SubmissionDTO submitTask(Long userId, Long taskId, String content) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        SubmissionEntity submission = new SubmissionEntity();
        submission.setUser(user);
        submission.setTask(task);
        submission.setContent(content);
        submission.setStatus(SubmissionEntity.Status.SUBMITTED);
        submissionRepository.save(submission);
        return SubmissionMapper.toDTO(submission);
    }

    public List<SubmissionDTO> getSubmissionsByUser(Long userId) {
        return submissionRepository.findAllByUserId(userId).stream()
                .map(SubmissionMapper::toDTO)
                .toList();
    }

    public List<SubmissionDTO> getSubmissionsByTask(Long taskId) {
        return submissionRepository.findAllByTaskId(taskId).stream()
                .map(SubmissionMapper::toDTO)
                .toList();
    }

    @Transactional
    public SubmissionDTO gradeSubmission(Long submissionId, int grade) {
        SubmissionEntity submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        submission.setGrade(grade);
        submission.setStatus(SubmissionEntity.Status.GRADED);

        return SubmissionMapper.toDTO(submission);
    }
}

