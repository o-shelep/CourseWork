package com.example.st_mission.repo;


import com.example.st_mission.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByCreatedById(Long teacherId);

    List<TaskEntity> findAllByCreatedById(Long teacherId);
}
