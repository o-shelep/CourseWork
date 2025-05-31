package com.example.st_mission.controller;


import com.example.st_mission.dto.submission.SubmissionDTO;
import com.example.st_mission.service.SubmissionService;
import com.example.st_mission.service.userDetails.UserDetailsImpl;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }


    @PostMapping("/{taskId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<SubmissionDTO> submitTask(@PathVariable Long taskId,
                                                    @RequestBody String content,
                                                    @AuthenticationPrincipal UserDetailsImpl student) {
        SubmissionDTO submission = submissionService.submitTask(student.getId(), taskId, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(submission);
    }


    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<SubmissionDTO>> getMySubmissions(@AuthenticationPrincipal UserDetailsImpl student) {
        return ResponseEntity.ok(submissionService.getSubmissionsByUser(student.getId()));
    }


    @GetMapping("/task/{taskId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsForTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(submissionService.getSubmissionsByTask(taskId));
    }


    @PatchMapping("/{submissionId}/grade")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<SubmissionDTO> gradeSubmission(@PathVariable Long submissionId,
                                                         @RequestParam @Min(0) int grade) {
        SubmissionDTO graded = submissionService.gradeSubmission(submissionId, grade);
        return ResponseEntity.ok(graded);
    }
}

