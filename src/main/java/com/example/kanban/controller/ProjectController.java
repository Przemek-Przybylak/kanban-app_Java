package com.example.kanban.controller;

import com.example.kanban.DTO.*;
import com.example.kanban.model.Project;
import com.example.kanban.model.Task;
import com.example.kanban.service.ProjectService;
import com.example.kanban.util.LocationUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("projects/{projectId}/tasks")
    public ResponseEntity<TaskResponseDto> addTask(@PathVariable String projectId, @RequestBody TaskRequestDto taskDto) {
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
    public ResponseEntity<ProjectResponseDto> addProject(@RequestBody ProjectRequestDto projectDto) {
        Project project = Mapper.fromDto(projectDto);

        Project savedProject = projectService.addProject(project);

        ProjectResponseDto projectResponseDto = Mapper.toDto(savedProject);

        URI location = LocationUtil.buildLocation(savedProject);

        return ResponseEntity.created(location).body(projectResponseDto);
    }

    @PutMapping("projects/{id}")
    public ResponseEntity<ProjectResponseDto> editProject(@PathVariable String id, @RequestBody ProjectRequestDto projectDto) {
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
