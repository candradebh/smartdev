package com.smartdev.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scheduled_tasks")
public class ScheduledTaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String serviceName;

    @Column(nullable = false)
    private String cronExpression;

    @Column(nullable = false)
    private String description;

    @Column(nullable = true)
    private LocalDateTime lastExecutionTime;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isActive;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String error;

    public ScheduledTaskEntity(Long id, String serviceName, String cronExpression, String description, boolean isActive) {
        this.id = id;
        this.serviceName = serviceName;
        this.cronExpression = cronExpression;
        this.description = description;
        this.isActive = isActive;

    }

    // Getters e Setters
}
