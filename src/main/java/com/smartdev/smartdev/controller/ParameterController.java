package com.smartdev.smartdev.controller;

import com.smartdev.smartdev.entity.ParametersEntity;
import com.smartdev.smartdev.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parameters")
public class ParameterController {

    @Autowired
    private ParameterRepository repository;

    @GetMapping
    public List<ParametersEntity> getAllConnectors() {
        return repository.findAll();
    }

}
