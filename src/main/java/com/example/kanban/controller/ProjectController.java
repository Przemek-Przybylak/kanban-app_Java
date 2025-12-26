package com.example.kanban.controller;

import com.example.kanban.DTO.*;
import com.example.kanban.model.Project;
import com.example.kanban.model.Task;
import com.example.kanban.service.ProjectService;
import com.example.kanban.util.LocationUtil;
import com.example.kanban.validation.OnCreate;
import com.example.kanban.validation.OnUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("projects/{projectId}/tasks")
    public ResponseEntity<List<TaskResponseDto>> getTaskByProject(@PathVariable String projectId) {
        List<Task> projectTasks = projectService.getTaskByProject(projectId);

        List<TaskResponseDto> tasksResponseDto = projectTasks.stream()
                .map(Mapper::toDto)
                .toList();

        return ResponseEntity.ok(tasksResponseDto);
    }

    @PostMapping("projects/{projectId}/tasks")
    public ResponseEntity<TaskResponseDto> addTask(@PathVariable String projectId, @Validated(OnCreate.class) @RequestBody TaskRequestDto taskDto) {
        Task task = Mapper.fromDto(taskDto);
        Task savedTask = projectService.addTask(projectId, task);

        TaskResponseDto taskResponseDto = Mapper.toDto(savedTask);

        URI location = LocationUtil.buildLocation(savedTask);

        return ResponseEntity.created(location).body(taskResponseDto);
    }

    @GetMapping("projects")
    public ResponseEntity<List<ProjectResponseDto>> getProjects() {
        List<Project> projects = projectService.getAllProjects();

        List<ProjectResponseDto> projectsResponseDto = projects.stream()
                .map(Mapper::toDto)
                .toList();

        return ResponseEntity.ok(projectsResponseDto);
    }

    @GetMapping("projects/{id}")
    public ResponseEntity<ProjectResponseDto> getProject(@PathVariable String id) {
        Project project = projectService.getProject(id);

        ProjectResponseDto projectResponseDto = Mapper.toDto(project);

        return ResponseEntity.ok(projectResponseDto);
    }

    @PostMapping("projects")
    public ResponseEntity<ProjectResponseDto> addProject(@Validated(OnCreate.class) @RequestBody ProjectRequestDto projectDto, Authentication authentication) {
        Project project = Mapper.fromDto(projectDto);

        String username = authentication.getName();

        Project savedProject = projectService.addProject(project, username);

        ProjectResponseDto projectResponseDto = Mapper.toDto(savedProject);

        URI location = LocationUtil.buildLocation(savedProject);

        return ResponseEntity.created(location).body(projectResponseDto);
    }

    @PutMapping("projects/{id}")
    public ResponseEntity<ProjectResponseDto> editProject(@PathVariable String id, @Validated(OnUpdate.class) @RequestBody ProjectRequestDto projectDto) {
        Project project = Mapper.fromDto(projectDto);

        Project savedProject = projectService.editProject(id, project);

        ProjectResponseDto projectResponseDto = Mapper.toDto(savedProject);

        return ResponseEntity.ok(projectResponseDto);
    }

    @PatchMapping("projects/{id}")
    public ResponseEntity<ProjectResponseDto> editPartialProject(@PathVariable String id, @RequestBody ProjectRequestDto projectDto) {
        Project project = Mapper.fromDto(projectDto);
        Project savedProject = projectService.editPartialProject(id, project);

        ProjectResponseDto projectResponseDto = Mapper.toDto(savedProject);

        return ResponseEntity.ok(projectResponseDto);
    }

    @DeleteMapping("projects/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {

        projectService.deleteProject(id);

        return ResponseEntity.noContent().build();
    }
}
