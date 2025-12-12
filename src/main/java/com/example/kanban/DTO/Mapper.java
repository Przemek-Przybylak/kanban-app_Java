package com.example.kanban.DTO;

import com.example.kanban.model.Project;

public class Mapper {

    public static ProjectResponseDto toTdo(Project entity) {
        return new ProjectResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getMembers(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getTasks().stream()
                        .map(task -> new shortTasksDto(task.getId(),task.getTitle()))
                        .toList()
        );
    }
}
