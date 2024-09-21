package com.smartdev.smartdev.controller;

import com.smartdev.smartdev.entity.AuditLogEntity;
import com.smartdev.smartdev.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @GetMapping
    public List<AuditLogEntity> getAllLogs() {
        return auditLogRepository.findAll();
    }

    @GetMapping("/{entityName}")
    public List<AuditLogEntity> getAuditLogsEntity(@PathVariable String entityName) {
        return auditLogRepository.findByEntityName(entityName);
    }

    @GetMapping("/{entityName}/{entityId}")
    public List<AuditLogEntity> getAuditLogsEntityNameEntityId(@PathVariable String entityName, @PathVariable Long entityId) {
        return auditLogRepository.findByEntityNameAndEntityId(entityName, entityId);
    }
}
