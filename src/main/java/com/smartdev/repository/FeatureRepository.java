package com.smartdev.repository;

import com.smartdev.entity.FeatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeatureRepository extends JpaRepository<FeatureEntity, Long> {

    List<FeatureEntity> findByProjectId(Long projectId);

}
