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
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<TaskResponseDto>> getTaskByProject(@PathVariable String projectId) {
        List<Task> projectTasks = projectService.getTaskByProject(projectId);

        List<TaskResponseDto> tasksResponseDto = projectTasks.stream()
                .map(Mapper::toDto)
                .toList();

        return ResponseEntity.ok(tasksResponseDto);
    }

    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<TaskResponseDto> addTask(@PathVariable String projectId, @Validated(OnCreate.class) @RequestBody TaskRequestDto taskDto, Authentication authentication) {
        Task task = Mapper.fromDto(taskDto);
        String username = authentication.getName();

        Task savedTask = projectService.addTask(projectId, task, username);

        TaskResponseDto taskResponseDto = Mapper.toDto(savedTask);

        URI location = LocationUtil.buildLocation(savedTask);

        return ResponseEntity.created(location).body(taskResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getProjects() {
        List<Project> projects = projectService.getAllProjects();

        List<ProjectResponseDto> projectsResponseDto = projects.stream()
                .map(Mapper::toDto)
                .toList();

        return ResponseEntity.ok(projectsResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> getProject(@PathVariable String id) {
        Project project = projectService.getProject(id);

        ProjectResponseDto projectResponseDto = Mapper.toDto(project);

        return ResponseEntity.ok(projectResponseDto);
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDto> addProject(@Validated(OnCreate.class) @RequestBody ProjectRequestDto projectDto, Authentication authentication) {
        Project project = Mapper.fromDto(projectDto);

        String username = authentication.getName();

        Project savedProject = projectService.addProject(project, username);

        ProjectResponseDto projectResponseDto = Mapper.toDto(savedProject);

        URI location = LocationUtil.buildLocation(savedProject);

        return ResponseEntity.created(location).body(projectResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> editProject(@PathVariable String id, @Validated(OnUpdate.class) @RequestBody ProjectRequestDto projectDto) {
        Project project = Mapper.fromDto(projectDto);

        Project savedProject = projectService.editProject(id, project);

        ProjectResponseDto projectResponseDto = Mapper.toDto(savedProject);

        return ResponseEntity.ok(projectResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> editPartialProject(@PathVariable String id, @RequestBody ProjectPatchRequestDto projectDto) {
        Project project = Mapper.fromDto(projectDto);
        Project savedProject = projectService.editPartialProject(id, project);

        ProjectResponseDto projectResponseDto = Mapper.toDto(savedProject);

        return ResponseEntity.ok(projectResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {

        projectService.deleteProject(id);

        return ResponseEntity.noContent().build();
    }
}
