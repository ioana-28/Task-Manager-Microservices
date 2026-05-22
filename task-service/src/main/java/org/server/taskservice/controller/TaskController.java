package org.server.taskservice.controller;

import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.server.taskservice.dto.TaskCreateRequestDto;
import org.server.taskservice.dto.TaskResponseDto;
import org.server.taskservice.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {

        this.taskService = taskService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDto>> getTasksForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksForUser(userId));
    }


    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskCreateRequestDto request) {
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<@NotNull List<@NotNull TaskResponseDto>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NotNull Void> deleteTask(@PathVariable @NotNull Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
