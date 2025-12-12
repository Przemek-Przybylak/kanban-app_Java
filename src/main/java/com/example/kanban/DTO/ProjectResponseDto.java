package com.example.kanban.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectResponseDto(String id, String title, String description, List<String> members,
                                 LocalDateTime createdAt, LocalDateTime updatedAt, List<shortTasksDto> tasks) {
}
