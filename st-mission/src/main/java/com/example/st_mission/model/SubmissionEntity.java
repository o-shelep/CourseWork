package com.example.st_mission.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "submission_entity")
public class SubmissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    @JsonManagedReference
    private TaskEntity task;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity user;

    private String content;

    private LocalDateTime submittedAt;

    private int grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.SUBMITTED;

    public enum Status {
        SUBMITTED,
        GRADED
    }

    @PrePersist
    protected void onSubmit() {
        this.submittedAt = LocalDateTime.now();
    }


//    public void setContentBasedOnSubmissionType(TaskEntity task, Object data) {
//        if (task.getSubmissionType() == TaskEntity.SubmissionType.FILE) {
//            if (!(data instanceof String)) {
//                throw new IllegalArgumentException("Content must be a file path as a String for FILE submission type");
//            }
//        } else if (task.getSubmissionType() == TaskEntity.SubmissionType.DOCUMENT || task.getSubmissionType() == TaskEntity.SubmissionType.MESSAGE) {
//            if (!(data instanceof String)) {
//                throw new IllegalArgumentException("Content must be text as a String for DOCUMENT or MESSAGE submission types");
//            }
//        }
//        this.content = (String) data;
//    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
