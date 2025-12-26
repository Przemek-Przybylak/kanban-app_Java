package com.example.kanban.service;

import com.example.kanban.model.Project;
import com.example.kanban.model.ProjectRepository;
import com.example.kanban.model.Task;
import com.example.kanban.model.TaskRepository;
import com.example.kanban.user.model.User;
import com.example.kanban.user.repository.UserRepository;
import com.example.kanban.user.service.UserService;
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
    private final UserService userService;
    private final UserRepository userRepository;


    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository, UserService userService, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public List<Task> getTaskByProject(String id) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getProject().getId() != null && task.getProject().getId().equals(id))
                .toList();
    }

    @Transactional
    @Override
    public Task addTask(String projectId, Task task, String username) {
        Project project = checkProjectExist(projectId);
        User owner = getOwner(username);

        task.setUser(owner);
        task.setProject(project);

        return taskRepository.save(task);
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
    public Project addProject(Project project, String username) {
        User owner = getOwner(username);

        project.getUsers().add(owner);
        owner.getProjects().add(project);
        return projectRepository.save(project);
    }

    @Transactional
    @Override
    public Project editProject(String id, Project project) {
        Project existingProject = checkProjectExist(id);

        existingProject.setTitle(project.getTitle());
        existingProject.setDescription(project.getDescription());
        existingProject.setMembers(project.getMembers());

        return projectRepository.save(existingProject);
    }

    @Transactional
    @Override
    public Project editPartialProject(String id, Project project) {
        Project existingProject = checkProjectExist(id);

        updateIfNotNull(project.getMembers(), existingProject::setMembers);
        updateIfNotNull(project.getDescription(), existingProject::setDescription);
        updateIfNotNull(project.getTitle(), existingProject::setTitle);
        updateIfNotNull(project.getTasks(), existingProject::setTasks);

        return projectRepository.save(existingProject);
    }

    @Transactional
    @Override
    public void deleteProject(String id) {
        Project project = checkProjectExist(id);

        projectRepository.delete(project);
    }

    private Project checkProjectExist(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
    }

    private User getOwner(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}

