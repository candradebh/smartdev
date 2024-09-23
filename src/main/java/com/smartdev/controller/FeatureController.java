package com.smartdev.controller;

import com.smartdev.entity.FeatureEntity;
import com.smartdev.entity.ProjectEntity;
import com.smartdev.repository.FeatureRepository;
import com.smartdev.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/features")
public class FeatureController {

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public List<FeatureEntity> getAllFeatures() {
        return featureRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeatureEntity> getFeatureById(@PathVariable Long id) {
        return featureRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FeatureEntity> createFeature(@RequestBody FeatureEntity feature, @RequestParam Long projectId) {
        return projectRepository.findById(projectId).map(new Function<ProjectEntity, ResponseEntity<FeatureEntity>>() {
            @Override
            public ResponseEntity<FeatureEntity> apply(ProjectEntity project) {
                feature.setProject(project);
                FeatureEntity newFeature = featureRepository.save(feature);
                return ResponseEntity.ok(newFeature);
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeatureEntity> updateFeature(@PathVariable Long id, @RequestBody FeatureEntity featureDetails) {
        return featureRepository.findById(id).map(new Function<FeatureEntity, ResponseEntity<FeatureEntity>>() {
            @Override
            public ResponseEntity<FeatureEntity> apply(FeatureEntity feature) {
                //feature.setFeatureValue(featureDetails.getFeatureValue());
                //feature.setTag(featureDetails.getTag());
                FeatureEntity updatedFeature = featureRepository.save(feature);
                return ResponseEntity.ok(updatedFeature);
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeature(@PathVariable Long id) {
        Optional<FeatureEntity> featureOpt = featureRepository.findById(id);
        if (featureOpt.isPresent()) {
            featureRepository.delete(featureOpt.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
