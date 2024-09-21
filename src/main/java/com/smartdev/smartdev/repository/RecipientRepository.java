package com.smartdev.smartdev.repository;

import com.smartdev.smartdev.entity.RecipientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipientRepository extends JpaRepository<RecipientEntity, Long> {
    List<RecipientEntity> findByIsActiveTrue();

}
