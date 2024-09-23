package com.smartdev.repository;

import com.smartdev.entity.RecipientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipientRepository extends JpaRepository<RecipientEntity, Long> {
    List<RecipientEntity> findByIsActiveTrue();

}
