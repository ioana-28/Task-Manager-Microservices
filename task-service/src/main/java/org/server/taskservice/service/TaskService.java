package org.server.taskservice.service;

import org.server.taskservice.dto.TaskCreateRequestDto;
import org.server.taskservice.dto.TaskResponseDto;

import java.util.List;

public interface TaskService {
    List<TaskResponseDto> getTasksForUser(Long  ownerUserId);
    TaskResponseDto createTask (TaskCreateRequestDto request);
    List<TaskResponseDto> getAllTasks();
    void deleteTask(Long id);

}
