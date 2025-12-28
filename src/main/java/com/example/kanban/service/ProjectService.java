package com.example.kanban.service;

import com.example.kanban.DTO.*;
import com.example.kanban.model.Project;
import com.example.kanban.model.ProjectRepository;
import com.example.kanban.model.Task;
import com.example.kanban.model.TaskRepository;
import com.example.kanban.user.model.User;
import com.example.kanban.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.example.kanban.util.UpdateIfNotNull.updateIfNotNull;

@Service
public class ProjectService implements ProjectServiceInterface {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public List<TaskResponseDto> getTaskByProject(String id) {
        return taskRepository.findByProjectId(id).stream()
                .map(Mapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public TaskResponseDto addTask(String projectId, TaskRequestDto taskDto, String username) {
        Project project = getProjectIfExisting(projectId);
        User owner = getOwner(username);

        Task task = Mapper.fromDto(taskDto);

        task.setUser(owner);
        task.setProject(project);

        taskRepository.save(task);

        return Mapper.toDto(task);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProjectResponseDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(Mapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ProjectResponseDto getProject(String id) {
        Project project = getProjectIfExisting(id);

        return Mapper.toDto(project);
    }

    @Transactional
    @Override
    public ProjectResponseDto addProject(ProjectRequestDto projectDto, String username) {
        User owner = getOwner(username);
        Project project = Mapper.fromDto(projectDto);

        project.getUsers().add(owner);
        owner.getProjects().add(project);

        Project savedProject = projectRepository.save(project);

        return Mapper.toDto(savedProject);
    }

    @Transactional
    @Override
    public ProjectResponseDto editProject(String id, ProjectRequestDto projectDto) {
        Project existingProject = getProjectIfExisting(id);

        existingProject.setTitle(projectDto.title());
        existingProject.setDescription(projectDto.description());

        Project savedProject = projectRepository.save(existingProject);

        return Mapper.toDto(savedProject);
    }

    @Transactional
    @Override
    public ProjectResponseDto editPartialProject(String id, ProjectPatchRequestDto project) {
        Project existingProject = getProjectIfExisting(id);

        updateIfNotNull(project.description(), existingProject::setDescription);
        updateIfNotNull(project.title(), existingProject::setTitle);

        Project savedProject = projectRepository.save(existingProject);

        return Mapper.toDto(savedProject);
    }

    @Transactional
    @Override
    public void deleteProject(String id) {
        Project project = getProjectIfExisting(id);

        projectRepository.delete(project);
    }

    private Project getProjectIfExisting(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
    }

    private User getOwner(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}

