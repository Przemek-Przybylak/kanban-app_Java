package com.example.kanban.model;

import com.example.kanban.util.HasId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "tasks")
public class Task implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, name = "task_id")
    private String id;

    private String title;

    @Column(length = 1000)
    private String description;

    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private String status;

    @ElementCollection
    @CollectionTable(
            name = "task_members",
            joinColumns = @JoinColumn(name = "task_id")
    )
    @Column(name = "member")
    private List<String> members;

    private String approvedBy;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties("tasks")
    private Project project = new Project();

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "todo";
        }
    }
}
