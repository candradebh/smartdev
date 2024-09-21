package com.smartdev.smartdev.repository;

import com.smartdev.smartdev.entity.LLMModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LLMModelRepository extends JpaRepository<LLMModelEntity, Long> {

    Optional<LLMModelEntity> findByName(String name);
}
