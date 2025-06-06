package com.example.st_mission.repo;


import com.example.st_mission.model.SubmissionEntity;
import com.example.st_mission.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<SubmissionEntity, Long> {
    List<SubmissionEntity> findAllByUserId(Long userId);
    List<SubmissionEntity> findAllByTaskId(Long taskId);

    List<SubmissionEntity> findByUserAndStatus(UserEntity user, SubmissionEntity.Status status);
}
