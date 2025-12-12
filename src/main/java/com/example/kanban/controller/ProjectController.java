package com.example.kanban.controller;

import com.example.kanban.DTO.Mapper;
import com.example.kanban.DTO.ProjectResponseDto;
import com.example.kanban.model.Project;
import com.example.kanban.model.ProjectRepository;
import com.example.kanban.service.ProjectService;
import com.example.kanban.util.LocationUtil;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    public ProjectController(ProjectRepository projectRepository, ProjectService projectService) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }

    @GetMapping("projects")
    public ResponseEntity<List<ProjectResponseDto>> getProjects() {
        List<Project> projects = projectService.getAllProjects();

        List<ProjectResponseDto> projectsResponseDto = projects.stream()
                .map(Mapper::toTdo)
                .toList();

        return ResponseEntity.ok(projectsResponseDto);
    }

    @GetMapping("projects/{id}")
    public ResponseEntity<ProjectResponseDto> getProject(@PathVariable String id) {
        Project project = projectService.getProject(id);

        ProjectResponseDto projectResponseDto = Mapper.toTdo(project);

        return ResponseEntity.ok(projectResponseDto);
    }

    @PostMapping("projects")
    public ResponseEntity<ProjectResponseDto> addProject(@RequestBody Project project) {
        Project savedProject = projectService.addProject(project);

        ProjectResponseDto projectResponseDto = Mapper.toTdo(savedProject);

        URI location = LocationUtil.buildLocation(savedProject);

        return ResponseEntity.created(location).body(projectResponseDto);
    }

    @PutMapping("projects/{id}")
    public ResponseEntity<ProjectResponseDto> editProject(@PathVariable String id, @RequestBody Project project) {
        Project savedProject = projectService.editProject(id, project);

        ProjectResponseDto projectResponseDto = Mapper.toTdo(savedProject);

        return ResponseEntity.ok(projectResponseDto);
    }

    @PatchMapping("projects/{id}")
    public ResponseEntity<ProjectResponseDto> editPartialProject(@PathVariable String id, @RequestBody Project project) {
        Project savedProject = projectService.editPartialProject(id,project);

        ProjectResponseDto projectResponseDto = Mapper.toTdo(savedProject);

        return ResponseEntity.ok(projectResponseDto);
    }

    @DeleteMapping("projects/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {

        projectService.deleteProject(id);

        return ResponseEntity.noContent().build();
    }
}
