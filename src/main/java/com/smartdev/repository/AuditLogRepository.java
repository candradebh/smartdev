package com.smartdev.repository;

import com.smartdev.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Long> {

    List<AuditLogEntity> findByEntityName(String entityName);

    List<AuditLogEntity> findByEntityNameAndEntityId(String entityName, Long entityId);

    void deleteByChangedAtBefore(LocalDateTime cutoffDate);
}
