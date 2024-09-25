package com.smartdev.controller;

import com.smartdev.entity.TechnologyEntity;
import com.smartdev.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technologies")
public class TechnologyController {

    @Autowired
    private TechnologyRepository technologyRepository;

    @GetMapping
    public List<TechnologyEntity> getAllTechnologies() {
        return technologyRepository.findAll();
    }

    @PostMapping
    public TechnologyEntity createTechnology(@RequestBody TechnologyEntity technology) {
        return technologyRepository.save(technology);
    }
}
