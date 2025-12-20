package com.example.demo.service;

import com.example.kanban.model.Project;
import com.example.kanban.model.ProjectRepository;
import com.example.kanban.model.Task;
import com.example.kanban.model.TaskRepository;
import com.example.kanban.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    ProjectRepository projectRepository;

    @InjectMocks
    TaskService taskService;

    @Test
    void shouldReturnTaskWhenExists() {
        Task task = new Task();
        task.setId("123");

        when(taskRepository.findById("123"))
                .thenReturn(Optional.of(task));

        Task result = taskService.getTask("123");

        assertEquals("123", result.getId());
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        when(taskRepository.findById("123"))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> taskService.getTask("123"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void shouldEditTaskPartially() {
        Project project = new Project();
        project.setId("p1");

        Task existingTask = new Task();
        existingTask.setId("123");
        existingTask.setTitle("example");
        existingTask.setProject(project);

        when(taskRepository.findById("123"))
                .thenReturn(Optional.of(existingTask));

        when(projectRepository.findById("p1"))
                .thenReturn(Optional.of(project));

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Task changedTask = new Task();
        changedTask.setTitle("example2");

        Task result = taskService.editPartialTask("123", changedTask);

        assertEquals("example2", result.getTitle());
    }

    @Test
    void shouldDeleteTask() {
        doNothing().when(taskRepository).deleteById("123");

        taskService.deleteTask("123");
        
        verify(taskRepository).deleteById("123");
    }

    @Test
    void shouldThrowExceptionWhenTaskToDeleteNotExist() {
        doThrow(EmptyResultDataAccessException.class)
                .when(taskRepository)
                .deleteById("123");

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class,
                        () -> taskService.deleteTask("123"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

}
