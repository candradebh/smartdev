package com.smartdev.controller;

import com.smartdev.entity.FeatureEntity;
import com.smartdev.entity.ProjectEntity;
import com.smartdev.repository.ProjectRepository;
import com.smartdev.services.GitHubService;
import com.smartdev.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private static final Logger logger = Logger.getLogger(ProjectController.class.getName());

    @Value("${github.repository}")
    private String gitHubRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<ProjectEntity> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/isActive")
    public List<ProjectEntity> getActiveProjects() {
        return projectRepository.findByIsActiveTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectEntity> getProjectById(@PathVariable Long id) {
        return projectRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProjectEntity createProject(@RequestBody ProjectEntity project) {
        return projectService.createRepositoryInGitAndClone(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectEntity> updateProject(@PathVariable Long id, @RequestBody ProjectEntity projectDetails) {

        ProjectEntity v_project = projectService.updateProject(id, projectDetails);
        if (v_project != null) {
            return ResponseEntity.ok(v_project);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        Optional<ProjectEntity> existing = projectRepository.findById(id);
        if (existing.isPresent()) {
            projectRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/{projectId}/features")
    public ResponseEntity<ProjectEntity> addFeatureToProject(@PathVariable Long projectId, @RequestBody FeatureEntity feature) {

        ProjectEntity project = projectService.addFeatureToProject(projectId, feature);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/{projectId}/features")
    public ResponseEntity<List<FeatureEntity>> getFeaturesByProjectId(@PathVariable Long projectId) {
        List<FeatureEntity> features = projectService.getFeaturesByProjectId(projectId);
        return ResponseEntity.ok(features);
    }

}
