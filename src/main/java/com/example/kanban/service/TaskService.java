package com.example.kanban.service;

import com.example.kanban.model.Project;
import com.example.kanban.model.ProjectRepository;
import com.example.kanban.model.Task;
import com.example.kanban.model.TaskRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService implements TaskServiceInterface {
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
        return checkTaskExisting(id);
    }

    @Transactional
    @Override
    public Task addTask(String projectId, Task task) {
        Project project = checkProjectExist(projectId);

        task.setProject(project);
        return taskRepository.save(task);
    }


    @Transactional
    @Override
    public Task editTask(String projectId, Task task) {
        checkTaskExisting(task.getId());

        Project project = checkProjectExist(projectId);

        return taskRepository.save(task);

    }

    @Transactional
    @Override
    public Task editPartialTask(String taskId, Task task) {
        Task existingTask = checkTaskExisting(taskId);

        String projectId = existingTask.getProject().getId();
        System.out.println("Project id to: " + projectId);
        Project project = checkProjectExist(projectId);
        System.out.println("Projekt to: " + project);
        existingTask.setProject(project);

        if (task.getMembers() != null) {
            existingTask.getMembers().clear();
            existingTask.getMembers().addAll(task.getMembers());
        }
        if (task.getDescription() != null) {
            existingTask.setDescription(task.getDescription());
        }
        if (task.getStatus() != null) {
            existingTask.setStatus(task.getStatus());
        }
        if (task.getApprovedBy() != null) {
            existingTask.setApprovedBy(task.getApprovedBy());
        }
        if (task.getDueDate() != null) {
            existingTask.setDueDate(task.getDueDate());
        }
        if (task.getTitle() != null) {
            existingTask.setTitle(task.getTitle());
        }
        if (task.getCreatedAt() != null) {
            existingTask.setCreatedAt(task.getCreatedAt());
        }

        return taskRepository.save(existingTask);
    }

    @Transactional
    @Override
    public void deleteTask(String id) {
        try {
            taskRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw (new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        }
    }

    private Project checkProjectExist(String projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
    }

    private Task checkTaskExisting(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }
}
