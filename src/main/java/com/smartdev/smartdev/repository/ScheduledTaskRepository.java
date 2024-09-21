package com.smartdev.smartdev.repository;

import com.smartdev.smartdev.entity.ScheduledTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduledTaskRepository extends JpaRepository<ScheduledTaskEntity, Long> {
    Optional<ScheduledTaskEntity> findByServiceName(String serviceName);

    List<ScheduledTaskEntity> findByIsActiveTrue();
}
