package com.example.kanban.service;

import com.example.kanban.model.Project;
import com.example.kanban.model.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProjectService implements ProjectServiceInterface{
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Project getProject(String id) {
        return checkProjectExist(id);
    }

    @Transactional
    @Override
    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    private Project checkProjectExist(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
    }
}

