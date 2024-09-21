package com.smartdev.smartdev.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smartdev.smartdev.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskEntity extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted = false;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;


    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_projects"))
    @JsonBackReference
    private ProjectEntity project;

}

