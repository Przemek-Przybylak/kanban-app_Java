package com.example.kanban.controller;

import com.example.kanban.model.Task;
import com.example.kanban.model.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import java.awt.*;

@RestController
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("tasks")
    public ResponseEntity<List<Task>> getALLTasks() {
        List<Task> allTasks = taskRepository.findAll();

        return ResponseEntity.ok(allTasks);
    }
}
