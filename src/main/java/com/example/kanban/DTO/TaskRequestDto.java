package com.example.kanban.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record TaskRequestDto(String title, String description, String status, LocalDateTime dueDate,
                             List<String> members, String approvedBy) {
}
