package com.example.kanban.service;

import com.example.kanban.model.Project;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectServiceInterface {

    @Transactional(readOnly = true)
    List<Project> getAllProjects();

    @Transactional(readOnly = true)
    Project getProject(String id);

    @Transactional
    Project addProject(Project project);
}
