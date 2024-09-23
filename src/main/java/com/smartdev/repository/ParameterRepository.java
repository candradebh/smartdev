package com.smartdev.repository;

import com.smartdev.entity.ParametersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParameterRepository extends JpaRepository<ParametersEntity, Long> {
    Optional<ParametersEntity> findByName(String name);
}
