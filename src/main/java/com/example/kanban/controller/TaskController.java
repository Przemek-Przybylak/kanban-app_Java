package com.example.kanban.controller;

import com.example.kanban.DTO.Mapper;
import com.example.kanban.DTO.TaskPatchRequestDto;
import com.example.kanban.DTO.TaskRequestDto;
import com.example.kanban.DTO.TaskResponseDto;
import com.example.kanban.model.Task;
import com.example.kanban.service.TaskService;
import com.example.kanban.validation.OnUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        List<Task> allTasks = taskService.getAllTasks();

        List<TaskResponseDto> tasksResponseDto = allTasks.stream()
                .map(Mapper::toDto)
                .toList();

        return ResponseEntity.ok(tasksResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable String id) {
        Task task = taskService.getTask(id);

        TaskResponseDto taskResponseDto = Mapper.toDto(task);

        return ResponseEntity.ok(taskResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> editTask(@PathVariable String id, @Validated(OnUpdate.class) @RequestBody TaskRequestDto taskDto) {
        Task task = Mapper.fromDto(taskDto);
        Task savedTask = taskService.editTask(id, task);

        TaskResponseDto taskResponseDto = Mapper.toDto(savedTask);

        return ResponseEntity.ok(taskResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDto> editPartialTask(@PathVariable String id, @RequestBody TaskPatchRequestDto taskDto) {
        Task task = Mapper.fromDto(taskDto);
        Task savedTask = taskService.editPartialTask(id, task);

        TaskResponseDto taskResponseDto = Mapper.toDto(savedTask);

        return ResponseEntity.ok(taskResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
}

