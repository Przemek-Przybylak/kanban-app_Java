package com.example.kanban.service;

import com.example.kanban.model.Project;
import com.example.kanban.model.ProjectRepository;
import com.example.kanban.model.Task;
import com.example.kanban.model.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService implements TaskServiceInterface{
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Task getTask(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    @Transactional
    @Override
    public Task addTask(String projectId, Task task) {
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        task.setProject(project);
        return taskRepository.save(task);
    }
}
