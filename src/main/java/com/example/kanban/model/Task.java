package com.example.kanban.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    private String taskId;

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
