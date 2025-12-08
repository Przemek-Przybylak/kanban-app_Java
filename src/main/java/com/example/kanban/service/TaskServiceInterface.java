package com.example.kanban.service;

import com.example.kanban.model.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskServiceInterface {
    @Transactional(readOnly = true)
    List<Task> getAllTasks();

    @Transactional(readOnly = true)
    Task getTask(String id);

    @Transactional
    Task addTask(String projectId, Task task);

    @Transactional
    Task editTask(String projectId, Task task);

    @Transactional
    Task editPartialTask(String taskId, Task task);

    @Transactional
    void deleteTask(String id);
}
