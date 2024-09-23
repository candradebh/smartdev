package com.smartdev.services;

import com.smartdev.controller.ProjectController;
import com.smartdev.entity.ProjectEntity;
import com.smartdev.utils.ReadmeGenerator;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@Service
public class GitHubService {
    private static final Logger logger = Logger.getLogger(ProjectController.class.getName());

    @Value("${github.repository}")
    private String githubRepository;

    @Value("${github.api.url}")
    private String githubApiUrl;

    @Value("${github.token}")
    private String githubToken;

    @Value("${github.username}")
    private String githubUsername;

    @Value("${github.password}")
    private String githubPassword;

    @Autowired
    private ReadmeGenerator readmeGenerator;

    /**
     * Crria um repositorio no Git
     *
     * @param project
     * @param isPrivate
     * @return
     * @throws IOException
     */
    public String createRepository(ProjectEntity project, boolean isPrivate) throws IOException {
        String repoUrl = project.getGitPath() != null && !project.getGitPath().isEmpty() ? project.getGitPath() : githubRepository + project.getName();

        try {

            GitHub github = new GitHubBuilder().withOAuthToken(githubToken).build();

            GHCreateRepositoryBuilder builder = github.createRepository(project.getName()).description(project.getDescription()).private_(isPrivate)
                    .autoInit(true); // Inicializa

            repoUrl = builder.create().getHtmlUrl().toString();

        } catch (Exception e) {
            logger.info("Erro ao criar o repositorio no git:\n" + e.getMessage());
            e.printStackTrace();
        }

        project.setGitPath(repoUrl);

        return repoUrl;
    }

    public void createDirAndCloneRepository(ProjectEntity project, String repoUrl) {
        if (repoUrl != null && repoUrl.isEmpty() == false) {

            File localRepoDirectory = new File(project.getPathProject());
            try {
                Git.cloneRepository().setURI(repoUrl).setDirectory(localRepoDirectory).call();
            } catch (InvalidRemoteException e) {
                logger.info("Erro ao clonar o repositorio (" + repoUrl + ") no git (InvalidRemoteException):\n" + e.getMessage());
                e.printStackTrace();
            } catch (TransportException e) {
                logger.info("Erro ao clonar o repositorio (" + repoUrl + ") no git (TransportException):\n" + e.getMessage());
                e.printStackTrace();
            } catch (GitAPIException e) {
                logger.info("Erro ao clonar o repositorio (" + repoUrl + ") no git (GitAPIException):\n" + e.getMessage());
                e.printStackTrace();
            }

        }
    }

    public void updateReadmeAndPush(ProjectEntity project) {

        try (Git git = Git.open(new File(project.getPathProject()))) {
            // escreve no arquivo o novo conteudo
            readmeGenerator.generateReadme(project);

            // Faz o commit das alterações
            git.add().addFilepattern("README.md").call();
            git.commit().setMessage("Atualizando README.md").call();

            // Faz o push das alterações
            git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(githubUsername, githubToken)).call();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidRemoteException e) {
            logger.info("Erro ao atualizar readme no repositorio (" + project.getGitPath() + ") no git (InvalidRemoteException):\n" + e.getMessage());
            e.printStackTrace();
        } catch (TransportException e) {
            logger.info("Erro ao atualizar readme no repositorio (" + project.getGitPath() + ") no git (TransportException):\n" + e.getMessage());
            e.printStackTrace();
        } catch (GitAPIException e) {
            logger.info("Erro ao atualizar readme no repositorio (" + project.getGitPath() + ") no git (GitAPIException):\n" + e.getMessage());
            e.printStackTrace();
        }

    }

}
