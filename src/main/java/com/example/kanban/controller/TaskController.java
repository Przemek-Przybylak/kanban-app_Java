package com.example.kanban.controller;

import com.example.kanban.model.Task;
import com.example.kanban.model.TaskRepository;
import com.example.kanban.service.TaskService;
import com.example.kanban.util.LocationUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    public TaskController(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @GetMapping("tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> allTasks = taskRepository.findAll();

        return ResponseEntity.ok(allTasks);
    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable String id) {
        Task task = taskService.getTask(id);

        return ResponseEntity.ok(task);
    }

    @PostMapping("tasks/{projectId}")
    public ResponseEntity<Task> addTask(@PathVariable String projectId, @RequestBody Task task) {
        Task savedTask = taskService.addTask(projectId, task);

        URI location = LocationUtil.buildLocation(savedTask);

        return ResponseEntity.created(location).body(savedTask);
    }

    @PutMapping("tasks/{projectId}")
    public ResponseEntity<Task> editTask(@PathVariable String projectId, @RequestBody Task task) {
        Task savedTask = taskService.editTask(projectId, task);

        return ResponseEntity.ok(savedTask);
    }

    @PatchMapping("tasks/{taskId}")
    public ResponseEntity<Task> editPartialTask(@PathVariable String id, @RequestBody Task task) {
        Task savedTask = taskService.editPartialTask(id, task);

        return ResponseEntity.ok(savedTask);
    }

    @DeleteMapping("tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
}

