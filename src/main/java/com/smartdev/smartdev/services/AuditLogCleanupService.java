package com.smartdev.smartdev.services;

import com.smartdev.smartdev.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class AuditLogCleanupService {
    @Value("${audit.log.retention-period}")
    private int retentionPeriodMonths;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Scheduled(cron = "0 9 17 * * ?")
    @Transactional
    public void cleanUpOldLogs() {
        LocalDateTime cutoffDate = LocalDateTime.now().minus(retentionPeriodMonths, ChronoUnit.MONTHS);
        auditLogRepository.deleteByChangedAtBefore(cutoffDate);
    }
}
