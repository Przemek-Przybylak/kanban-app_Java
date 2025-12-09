package com.example.kanban.controller;

import com.example.kanban.model.Project;
import com.example.kanban.model.ProjectRepository;
import com.example.kanban.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        List<Project> projects =  projectService.getAllProjects();

        return ResponseEntity.ok(projects);
    }

    @GetMapping("projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable String id) {
        Project project = projectService.getProject(id);

        return ResponseEntity.ok(project);
    }
}
