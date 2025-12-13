package com.example.kanban.DTO;

import java.util.List;

public record ProjectRequestDto(String title, String description, List<String> members) {
}
