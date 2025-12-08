package com.example.kanban.service;

import com.example.kanban.model.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskServiceInterface {
    List<Task> getAllTasks();

    @Transactional(readOnly = true)
    Task getTask(String id);

    @Transactional
    Task addTask(String projectId, Task task);
}
