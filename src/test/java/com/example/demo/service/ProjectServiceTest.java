package com.example.demo.service;

import com.example.kanban.DTO.ProjectPatchRequestDto;
import com.example.kanban.DTO.ProjectResponseDto;
import com.example.kanban.DTO.TaskRequestDto;
import com.example.kanban.DTO.TaskResponseDto;
import com.example.kanban.model.Project;
import com.example.kanban.model.ProjectRepository;
import com.example.kanban.model.Task;
import com.example.kanban.model.TaskRepository;
import com.example.kanban.service.ProjectService;
import com.example.kanban.user.model.User;
import com.example.kanban.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ProjectService projectService;

    @Test
    void shouldReturnProjectTasks() {
        Project project1 = new Project();
        project1.setId("p1");

        Project project2 = new Project();
        project2.setId("p2");

        Task task1 = new Task();
        task1.setId("t1");
        task1.setProject(project1);

        Task task2 = new Task();
        task1.setId("t2");
        task2.setProject(project1);

        Task task3 = new Task();
        task3.setId("t3");
        task3.setProject(project2);

        when(taskRepository.findAll())
                .thenReturn(List.of(task1, task2, task3));

        List<TaskResponseDto> result = projectService.getTaskByProject("p1");

        assertEquals(2, result.size());
    }

    @Test
    void shouldAddTask() {
        String projectId = "p1";
        String username = "admin";
        TaskRequestDto requestDto = new TaskRequestDto("t1", "desc", "todo", null, null);

        Project project = new Project();
        project.setId(projectId);

        User user = new User();
        user.setUsername(username);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        TaskResponseDto result = projectService.addTask(projectId, requestDto, username);

        assertNotNull(result);
        assertEquals("t1", result.title());
        assertEquals(projectId, result.project().id());

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void shouldThrowExceptionWhenProjectInTaskNotFound() {
        TaskRequestDto task = new TaskRequestDto("title", "desc", "todo", null, null);

        when(projectRepository.findById("p1"))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> projectService.addTask("p1", task, "u1"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void shouldReturnProjectWhenExist() {
        Project project = new Project();
        project.setId("123");

        when(projectRepository.findById("123"))
                .thenReturn(Optional.of(project));

        ProjectResponseDto result = projectService.getProject("123");

        assertEquals("123", result.id());
    }

    @Test
    void shouldThrowExceptionWhenProjectFound() {
        when(projectRepository.findById("123"))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> projectService.getProject("123"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void shouldEditProjectPartially() {
        Task task = new Task();
        task.setId("t1");

        Project existingProject = new Project();
        existingProject.setId("123");
        existingProject.setTitle("example");
        existingProject.setTasks(List.of(task));

        when(projectRepository.findById("123"))
                .thenReturn(Optional.of(existingProject));

        when(projectRepository.save(any(Project.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProjectPatchRequestDto changedProject = new ProjectPatchRequestDto("example2", null);

        ProjectResponseDto result = projectService.editPartialProject("123", changedProject);

        assertEquals("example2", result.title());
    }

    @Test
    void shouldDeleteProject() {
        Project project = new Project();
        project.setId("123");

        when(projectRepository.findById("123"))
                .thenReturn(Optional.of(project));

        projectService.deleteProject("123");

        verify(projectRepository).delete(project);
    }

    @Test
    void shouldThrowExceptionWhenProjectToDeleteNotExist() {
        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class,
                        () -> projectService.deleteProject("123"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
