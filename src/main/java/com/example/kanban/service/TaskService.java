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

import static com.example.kanban.util.UpdateIfNotNull.updateIfNotNull;

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
    public Task editTask(String id, Task task) {
        Task existingTask = checkTaskExisting(id);
        checkProjectExist(existingTask.getProject().getId());

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setApprovedBy(task.getApprovedBy());

        return taskRepository.save(existingTask);
    }

    @Transactional
    @Override
    public Task editPartialTask(String id, Task task) {
        Task existingTask = checkTaskExisting(id);
        Project project = checkProjectExist(existingTask.getProject().getId());
        existingTask.setProject(project);

        updateIfNotNull(task.getDescription(), existingTask::setDescription);
        updateIfNotNull(task.getStatus(), existingTask::setStatus);
        updateIfNotNull(task.getApprovedBy(), existingTask::setApprovedBy);
        updateIfNotNull(task.getDueDate(), existingTask::setDueDate);
        updateIfNotNull(task.getTitle(), existingTask::setTitle);
        updateIfNotNull(task.getCreatedAt(), existingTask::setCreatedAt);

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
