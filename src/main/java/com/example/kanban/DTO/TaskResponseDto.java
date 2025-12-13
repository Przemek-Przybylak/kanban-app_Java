package com.example.kanban.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record TaskResponseDto(String id, String title, String description, String status, List<String> members,
                              String approvedBy, LocalDateTime dueDate, LocalDateTime createdAt,
                              shortProjectDto project) {
}
