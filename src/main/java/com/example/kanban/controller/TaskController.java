package com.example.kanban.controller;

import com.example.kanban.DTO.Mapper;
import com.example.kanban.DTO.TaskRequestDto;
import com.example.kanban.DTO.TaskResponseDto;
import com.example.kanban.model.Task;
import com.example.kanban.service.TaskService;
import com.example.kanban.util.LocationUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("tasks")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        List<Task> allTasks = taskService.getAllTasks();

        List<TaskResponseDto> tasksResponseDto = allTasks.stream()
                .map(Mapper::toDto)
                .toList();

        return ResponseEntity.ok(tasksResponseDto);
    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable String id) {
        Task task = taskService.getTask(id);

        TaskResponseDto taskResponseDto = Mapper.toDto(task);

        return ResponseEntity.ok(taskResponseDto);
    }

    @PostMapping("tasks/{projectId}")
    public ResponseEntity<TaskResponseDto> addTask(@PathVariable String projectId, @RequestBody TaskRequestDto taskDto) {
        Task task = Mapper.fromDto(taskDto);
        Task savedTask = taskService.addTask(projectId, task);

        TaskResponseDto taskResponseDto = Mapper.toDto(savedTask);

        URI location = LocationUtil.buildLocation(savedTask);

        return ResponseEntity.created(location).body(taskResponseDto);
    }

    @PutMapping("tasks/{id}")
    public ResponseEntity<TaskResponseDto> editTask(@PathVariable String id, @RequestBody TaskRequestDto taskDto) {
        Task task = Mapper.fromDto(taskDto);
        Task savedTask = taskService.editTask(id, task);

        TaskResponseDto taskResponseDto = Mapper.toDto(savedTask);

        return ResponseEntity.ok(taskResponseDto);
    }

    @PatchMapping("tasks/{id}")
    public ResponseEntity<TaskResponseDto> editPartialTask(@PathVariable String id, @RequestBody TaskRequestDto taskDto) {
        Task task = Mapper.fromDto(taskDto);
        Task savedTask = taskService.editPartialTask(id, task);

        TaskResponseDto taskResponseDto = Mapper.toDto(savedTask);

        return ResponseEntity.ok(taskResponseDto);
    }

    @DeleteMapping("tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
}

