package com.example.kanban.DTO;

import java.util.List;

public record ProjectPatchRequestDto(
        String title,
        String description,
        List<String> members
) {}
