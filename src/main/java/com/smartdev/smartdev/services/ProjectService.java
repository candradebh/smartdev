package com.smartdev.smartdev.services;

import com.smartdev.smartdev.controller.ProjectController;
import com.smartdev.smartdev.entity.FeatureEntity;
import com.smartdev.smartdev.entity.ProjectEntity;
import com.smartdev.smartdev.repository.FeatureRepository;
import com.smartdev.smartdev.repository.ProjectRepository;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;

@Service
public class ProjectService {
    private static final Logger logger = Logger.getLogger(ProjectController.class.getName());

    @Value("${github.api.url}")
    private String githubApiUrl;

    @Value("${github.token}")
    private String githubToken;

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private FeatureRepository featureRepository;

    public String createRepository(String name, String description, boolean isPrivate) throws IOException {
        GitHub github = new GitHubBuilder().withOAuthToken(githubToken).build();
        GHCreateRepositoryBuilder builder = github.createRepository(name).description(description).private_(isPrivate).autoInit(true); // Inicializa com README

        // Adicione configurações adicionais conforme necessário

        return builder.create().getHtmlUrl().toString();
    }

    public ProjectEntity createRepositoryInGitAndClone(ProjectEntity project) {
        try {
            String repoUrl = gitHubService.createRepository(project, false);

            gitHubService.createDirAndCloneRepository(project, repoUrl);

            gitHubService.updateReadmeAndPush(project);
        } catch (Exception e) {
            logger.info("Falha ao tenta criar e/ou clonar o repositório");
        }

        return projectRepository.save(project);
    }

    public ProjectEntity addFeatureToProject(Long projectId, FeatureEntity feature) {
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(new Supplier<RuntimeException>() {
            @Override
            public RuntimeException get() {
                return new RuntimeException("Projeto não encontrado");
            }
        });
        // project.getFeatures().clear();
        // project = projectRepository.save(project);

        FeatureEntity v_feature = project.getFeatures().stream().filter(new Predicate<FeatureEntity>() {
            @Override
            public boolean test(FeatureEntity f) {
                return feature.getId() != null && f.getId() == feature.getId();
            }
        }).findFirst().orElse(null);

        if (v_feature == null) {
            feature.setProject(project);
            project.getFeatures().add(feature);
            featureRepository.save(feature);
        } else {
            //v_feature.setTag(feature.getTag());
            //v_feature.setFeatureValue(feature.getFeatureValue());
            featureRepository.save(v_feature);
        }

        return projectRepository.save(project);
    }

    public List<FeatureEntity> getFeaturesByProjectId(Long projectId) {
        return featureRepository.findByProjectId(projectId);
    }

}
