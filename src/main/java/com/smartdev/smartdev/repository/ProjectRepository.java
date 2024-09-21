package com.smartdev.smartdev.repository;

import com.smartdev.smartdev.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    List<ProjectEntity> findByIsActiveTrue();

    List<ProjectEntity> findByIsActiveFalse();
}
