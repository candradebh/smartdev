package com.smartdev.smartdev.repository;

import com.smartdev.smartdev.entity.TaskStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepository extends JpaRepository<TaskStatusEntity, Long> {
}
