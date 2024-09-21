package com.smartdev.smartdev.repository;

import com.smartdev.smartdev.entity.ProjectEntity;
import com.smartdev.smartdev.entity.TaskEntity;
import com.smartdev.smartdev.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByStatus(TaskStatus status);

    List<TaskEntity> findByProject(ProjectEntity project);

    // Buscar tasks que não foram deletadas
    List<TaskEntity> findByDeletedFalse();

    // Buscar tasks por status e que não foram deletadas
    List<TaskEntity> findByStatusAndDeletedFalse(TaskStatus status);
}

