package com.smartdev.services;

import com.smartdev.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParameterService {

    @Autowired
    private ParameterRepository repository;

}
