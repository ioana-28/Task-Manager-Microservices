package org.server.taskservice.service;

import org.server.taskservice.dto.TaskCreateRequestDto;
import org.server.taskservice.dto.TaskResponseDto;
import org.server.taskservice.entity.Task;
import org.server.taskservice.exception.ResourceNotFoundException;
import org.server.taskservice.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final WebClient.Builder webClientBuilder;
    private final String USER_SERVICE_URL = "http://localhost:8081/api/users/";

    public TaskServiceImpl(TaskRepository taskRepository, WebClient.Builder webClientBuilder) {
        this.taskRepository = taskRepository;
        this.webClientBuilder = webClientBuilder;
    }

    private TaskResponseDto mapToDto(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getOwnerUserId()
        );
    }

    @Override
    @Transactional
    public TaskResponseDto createTask(TaskCreateRequestDto request) {
        try {
            webClientBuilder.build()
                    .get()
                    .uri(USER_SERVICE_URL + request.ownerUserId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new ResourceNotFoundException("User not found with ID: " + request.ownerUserId());
        }
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setCreatedAt(new Date());
        task.setOwnerUserId(request.ownerUserId());

        return mapToDto(taskRepository.save(task));
    }



    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDto> getTasksForUser(Long ownerUserId) {
        return taskRepository.findAllByOwnerUserId(ownerUserId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
