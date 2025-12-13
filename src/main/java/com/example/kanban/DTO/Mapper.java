package com.example.kanban.DTO;

import com.example.kanban.model.Project;
import com.example.kanban.model.Task;

public class Mapper {

    public static ProjectResponseDto toDto(Project entity) {
        return new ProjectResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getMembers(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getTasks().stream()
                        .map(task -> new shortTasksDto(task.getId(), task.getTitle()))
                        .toList()
        );
    }

    public static TaskResponseDto toDto(Task entity) {
        return new TaskResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getMembers(),
                entity.getApprovedBy(),
                entity.getDueDate(),
                entity.getCreatedAt(),
                new shortProjectDto(entity.getProject().getId(), entity.getProject().getTitle())
        );
    }
}
