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

    public static Project fromDto(ProjectRequestDto dto) {
        Project project = new Project();

        project.setTitle(dto.title());
        project.setDescription(dto.description());
        project.setMembers(dto.members());

        return project;
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

    public static Task fromDto(TaskRequestDto taskDto) {
        Task task = new Task();

        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        task.setStatus(taskDto.status());
        task.setDueDate(taskDto.dueDate());
        task.setMembers(taskDto.members());
        task.setApprovedBy(taskDto.approvedBy());

        return task;
    }
}
