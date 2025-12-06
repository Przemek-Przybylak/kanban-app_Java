package com.example.kanban.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue
    @Column(name = "project_id", columnDefinition = "BINARY(16)")
    private UUID projectId;

    @Column(length = 1000)
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(
            name = "members",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Column(name = "member")
    private List<String> members;

    private String title;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
