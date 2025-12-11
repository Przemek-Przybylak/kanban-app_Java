package com.example.kanban.controller;

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
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> projects = projectService.getAllProjects();

        return ResponseEntity.ok(projects);
    }

    @GetMapping("projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable String id) {
        Project project = projectService.getProject(id);

        return ResponseEntity.ok(project);
    }

    @PostMapping("projects")
    public ResponseEntity<Project> addProject(@RequestBody Project project) {
        Project savedProject = projectService.addProject(project);

        URI location = LocationUtil.buildLocation(savedProject);

        return ResponseEntity.created(location).body(savedProject);
    }

    @PutMapping("projects/{id}")
    public ResponseEntity<Project> editProject(@PathVariable String id, @RequestBody Project project) {
        Project savedProject = projectService.editProject(id, project);

        return ResponseEntity.ok(savedProject);
    }

    @PatchMapping("projects/{id}")
    public ResponseEntity<Project> editPartialProject(@PathVariable String id, @RequestBody Project project) {
        Project savedProject = projectService.editPartialProject(id,project);

        return ResponseEntity.ok(savedProject);
    }

    @DeleteMapping("projects/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {

        projectService.deleteProject(id);

        return ResponseEntity.noContent().build();
    }
}
