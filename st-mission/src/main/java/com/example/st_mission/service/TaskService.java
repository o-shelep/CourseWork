package com.example.st_mission.service;


import com.example.st_mission.dto.task.TaskCreateDTO;
import com.example.st_mission.dto.task.TaskDTO;
import com.example.st_mission.mapper.TaskMapper;
import com.example.st_mission.model.TaskEntity;
import com.example.st_mission.model.UserEntity;
import com.example.st_mission.repo.TaskRepository;
import com.example.st_mission.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskMapper::toDTO)
                .toList();
    }

    public TaskDTO getTaskById(long id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with ID " + id + " not found"));
        return TaskMapper.toDTO(task);
    }

    @Transactional
    public TaskDTO createTask(TaskCreateDTO createDTO, Long userId) {
        UserEntity creator = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TaskEntity task = TaskMapper.toEntity(createDTO, creator);
        taskRepository.save(task);

        return TaskMapper.toDTO(task);
    }

    public List<TaskDTO> getAllTasksCreatedBy(Long teacherId) {
        return taskRepository.findAllByCreatedById(teacherId).stream()
                .map(TaskMapper::toDTO)
                .toList();
    }

    public TaskEntity getTaskEntityByIdAndTeacher(Long taskId, Long teacherId) throws AccessDeniedException {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getCreatedBy().getId().equals(teacherId)) {
            throw new AccessDeniedException("Not allowed to access this task");
        }
        return task;
    }

    public TaskDTO getTaskByIdAndTeacher(Long taskId, Long teacherId) throws AccessDeniedException {
        TaskEntity task = getTaskEntityByIdAndTeacher(taskId, teacherId);
        return TaskMapper.toDTO(task);
    }

    @Transactional
    public TaskDTO updateTaskByTeacher(Long taskId, TaskEntity input, Long teacherId) throws AccessDeniedException {
        TaskEntity task = getTaskEntityByIdAndTeacher(taskId, teacherId);


        task.setTitle(input.getTitle());
        task.setDescription(input.getDescription());
        task.setPoints(input.getPoints());
        task.setDeadline(input.getDeadline());


        return TaskMapper.toDTO(task);
    }

    @Transactional
    public void deleteTaskByTeacher(Long id, Long teacherId) throws AccessDeniedException {
        TaskEntity task = getTaskEntityByIdAndTeacher(id, teacherId);
        taskRepository.delete(task);
    }

}
