package com.smartdev.smartdev.services;

import com.smartdev.smartdev.entity.LogEntity;
import com.smartdev.smartdev.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public LogEntity createLog(String eventName, String description) {
        LogEntity log = new LogEntity(null, new Date(), eventName, description);
        return logRepository.save(log);
    }

    public List<LogEntity> getAllLogs() {
        return logRepository.findAll();
    }

    public LogEntity getLogById(Long id) {
        return logRepository.findById(id).orElse(null);
    }

    public void deleteLog(Long id) {
        logRepository.deleteById(id);
    }
}
