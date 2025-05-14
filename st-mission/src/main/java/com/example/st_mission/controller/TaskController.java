package com.example.st_mission.controller;


import com.example.st_mission.dto.task.TaskCreateDTO;
import com.example.st_mission.dto.task.TaskDTO;
import com.example.st_mission.mapper.TaskMapper;
import com.example.st_mission.service.TaskService;
import com.example.st_mission.service.userDetails.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@PreAuthorize("hasRole('TEACHER')")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid TaskCreateDTO dto,
                                              @AuthenticationPrincipal UserDetailsImpl teacher) {
        TaskDTO created = taskService.createTask(dto, teacher.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllMyTasks(@AuthenticationPrincipal UserDetailsImpl teacher) {
        return ResponseEntity.ok(taskService.getAllTasksCreatedBy(teacher.getId()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable long id,
                                               @AuthenticationPrincipal UserDetailsImpl teacher) throws AccessDeniedException {
        return ResponseEntity.ok(taskService.getTaskByIdAndTeacher(id, teacher.getId()));
    }


    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable long id,
                                              @RequestBody TaskCreateDTO updatedTask,
                                              @AuthenticationPrincipal UserDetailsImpl teacher) throws AccessDeniedException {

        var task = taskService.getTaskByIdAndTeacher(id, teacher.getId());
        var entity = TaskMapper.toEntity(updatedTask, null);
        entity.setId(id);
        return ResponseEntity.ok(taskService.updateTaskByTeacher(id, entity, teacher.getId()));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id,
                                           @AuthenticationPrincipal UserDetailsImpl teacher) throws AccessDeniedException {
        taskService.deleteTaskByTeacher(id, teacher.getId());
        return ResponseEntity.noContent().build();
    }


}
