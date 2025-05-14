package com.example.st_mission.mapper;


import com.example.st_mission.dto.task.TaskCreateDTO;
import com.example.st_mission.dto.task.TaskDTO;
import com.example.st_mission.model.TaskEntity;
import com.example.st_mission.model.UserEntity;

public class TaskMapper {

    public static TaskEntity toEntity(TaskCreateDTO dto, UserEntity creator) {
        TaskEntity task = new TaskEntity();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setPoints(dto.points());
        task.setDeadline(dto.deadline());
        task.setCreatedBy(creator);
        return task;
    }

    public static TaskDTO toDTO(TaskEntity entity) {
        return new TaskDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPoints(),
                entity.getDeadline(),
                entity.getCreatedBy().getName()
        );
    }
}

