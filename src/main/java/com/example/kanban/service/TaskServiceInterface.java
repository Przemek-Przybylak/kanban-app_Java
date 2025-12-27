package com.example.kanban.service;

import com.example.kanban.DTO.TaskPatchRequestDto;
import com.example.kanban.DTO.TaskRequestDto;
import com.example.kanban.DTO.TaskResponseDto;
import com.example.kanban.model.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskServiceInterface {
    @Transactional(readOnly = true)
    List<TaskResponseDto> getAllTasks();

    @Transactional(readOnly = true)
    TaskResponseDto getTask(String id);

    @Transactional
    TaskResponseDto editTask(String id, TaskRequestDto taskDto);

    @Transactional
    TaskResponseDto editPartialTask(String id, TaskPatchRequestDto task);

    @Transactional
    void deleteTask(String id);
}
