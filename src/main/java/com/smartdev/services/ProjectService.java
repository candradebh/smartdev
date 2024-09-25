package com.smartdev.services;

import com.smartdev.controller.ProjectController;
import com.smartdev.entity.FeatureEntity;
import com.smartdev.entity.ProjectEntity;
import com.smartdev.entity.TaskEntity;
import com.smartdev.enums.TaskStatus;
import com.smartdev.repository.FeatureRepository;
import com.smartdev.repository.ProjectRepository;
import com.smartdev.repository.TaskRepository;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;

@Service
public class ProjectService {
    private static final Logger logger = Logger.getLogger(ProjectController.class.getName());

    @Value("${github.api.url}")
    private String githubApiUrl;

    @Value("${github.repository}")
    private String gitHubRepository;

    @Value("${github.token}")
    private String githubToken;

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private TaskRepository taskRepository;


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

            //algumas tasks ja devem ser criadas
            project.getTasks().addAll(getListBasicsTasks(project));

        } catch (Exception e) {
            logger.info("Falha ao tenta criar projeto e/ou clonar o repositório");
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


    public ProjectEntity updateProject(Long id, ProjectEntity projectDetails) {

        Optional<ProjectEntity> v_projectOpt = projectRepository.findById(id);
        ProjectEntity project = null;

        if (v_projectOpt.isPresent()) {

            project = v_projectOpt.get();

            String repoUrl = gitHubRepository + project.getName();

            // nao entendi porque fiz isso
            if (projectDetails.getGitPath() != null && !projectDetails.getGitPath().equals(repoUrl)) {
                project.setGitPath(repoUrl);
            }

            // project.setName(projectDetails.getName());// C:/projetos/teste/
            project.setDescription(projectDetails.getDescription());
            project.setWorkspacePath(projectDetails.getWorkspacePath());
            project.setGitPath(projectDetails.getGitPath());
            project.setIsActive(projectDetails.getIsActive());
            // project.setFeatures(projectDetails.getFeatures());

            //adicionar tasks basicas
            project.getTasks().addAll(getListBasicsTasks(project));

            //apenas cria um novo readme , faz comit e push
            gitHubService.updateReadmeAndPush(projectRepository.save(project));

            return projectRepository.save(project);
        }

        return project;
    }

    private List<TaskEntity> getListBasicsTasks(ProjectEntity project) {

        List<TaskEntity> v_listBasicsTasks = new ArrayList<>();

        TaskEntity task1 = new TaskEntity();
        task1.setProject(project);
        task1.setTitle("Analisar código geral");
        task1.setDescription("Faz uma analise superficial do código para extrair algumas inforrmações.");
        task1.setStatus(TaskStatus.TODO);


        TaskEntity task2 = new TaskEntity();
        task2.setProject(project);
        task2.setTitle("Adicione Tecnologias ao seu projeto" + project.getName());
        task2.setDescription("Adicione Tecnologias ao seu projeto" + project.getName());
        task2.setStatus(TaskStatus.TODO);

        v_listBasicsTasks.add(task1);

        //remove se ja existir
        for (TaskEntity v_task : v_listBasicsTasks) {
            v_listBasicsTasks.removeIf(t -> t.getTitle().equals(v_task.getTitle()));
        }

        return v_listBasicsTasks;
    }

}
