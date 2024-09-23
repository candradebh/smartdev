package com.smartdev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "llm_models")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LLMModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private OffsetDateTime modified_at;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false, length = 1024)
    private String digest;

    @Embedded
    private LLMModelDetails details;
}
