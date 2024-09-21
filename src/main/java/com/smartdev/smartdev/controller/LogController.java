package com.smartdev.smartdev.controller;

import com.smartdev.smartdev.entity.LogEntity;
import com.smartdev.smartdev.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping
    public ResponseEntity<LogEntity> createLog(@RequestParam String eventName, @RequestParam String description) {
        LogEntity log = logService.createLog(eventName, description);
        return ResponseEntity.ok(log);
    }

    @GetMapping
    public ResponseEntity<List<LogEntity>> getAllLogs() {
        List<LogEntity> logs = logService.getAllLogs();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogEntity> getLogById(@PathVariable Long id) {
        LogEntity log = logService.getLogById(id);
        return ResponseEntity.ok(log);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        logService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }
}
