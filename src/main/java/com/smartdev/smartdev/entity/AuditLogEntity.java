package com.smartdev.smartdev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityName;

    private Long entityId;

    private String operationType; // CREATE, UPDATE, DELETE

    @Lob
    private String oldValue;

    @Lob
    private String newValue;

    private String changedBy;

    private LocalDateTime changedAt;

    // Getters and Setters
}
