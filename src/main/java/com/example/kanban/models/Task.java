package com.example.kanban.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue
    @Column(name = "task_id", columnDefinition = "BINARY(16)")
    private UUID taskId;

    private String title;

    @Column(length = 1000)
    private String description;

    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private String status;

    @ElementCollection
    @CollectionTable(
            name = "members",
            joinColumns = @JoinColumn(name = "task_id")
    )
    @Column(name = "members")
    private List<String> members;

    private String approvedBy;

}
