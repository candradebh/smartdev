package com.smartdev.smartdev.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "features")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeatureEntity extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "local_package_manager")
    private String localPackageManager;

    @ManyToOne
    @JoinColumn(name = "technology_id", foreignKey = @ForeignKey(name = "fk_features_technologies"))
    private TechnologyEntity technology;

    @ManyToOne
    @JoinColumn(name = "project_id", foreignKey = @ForeignKey(name = "fk_features_projects"))
    @JsonBackReference
    private ProjectEntity project;

}
