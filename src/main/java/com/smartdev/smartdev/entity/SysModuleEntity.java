package com.smartdev.smartdev.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "sysmodules")
public class SysModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}
