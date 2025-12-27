package com.example.kanban.service;

import com.example.kanban.DTO.TaskPatchRequestDto;
import com.example.kanban.model.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskServiceInterface {
    @Transactional(readOnly = true)
    List<Task> getAllTasks();

    @Transactional(readOnly = true)
    Task getTask(String id);

    @Transactional
    Task editTask(String id, Task task);

    @Transactional
    Task editPartialTask(String id, Task task);

    @Transactional
    void deleteTask(String id);
}
