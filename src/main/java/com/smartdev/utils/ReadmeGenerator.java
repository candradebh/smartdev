package com.smartdev.utils;

import com.smartdev.entity.FeatureEntity;
import com.smartdev.entity.ProjectEntity;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ReadmeGenerator {

    public String generateReadme(ProjectEntity project) throws IOException {
        String v_filePath = project.getPathProject() + "/README.md";

        String readmeContentProjeto = "# Projeto: " + project.getName() + "\n\n" +
                "**Data de Criação**: " + project.getCreatedAt() != null ? project.getCreatedAt().toString() : "N/A" + "\n" +
                "**Caminho do Projeto**: " + project.getPathProject() + "\n" + //
                "## Descrição \n\n" + project.getDescription() + "\n\n";


        // Estrutura para armazenar as linguagens e frameworks
        Map<String, String> languages = new HashMap<>();
        Map<String, String> frameworks = new HashMap<>();

        // Processa as features do projeto
        for (FeatureEntity feature : project.getFeatures()) {
            String tag = "";//feature.getTag();
            String value = "";//feature.getFeatureValue();

            if (tag.startsWith("LANGUAGE")) {
                String languageKey = tag.split("_")[0]; // Exemplo: LANGUAGE1
                if (tag.contains("_VERSION")) {
                    languages.put(languageKey, languages.getOrDefault(languageKey, "") + " v" + value);
                } else {
                    languages.put(languageKey, value);
                }
            } else if (tag.startsWith("FRAMEWORK")) {
                String frameworkKey = tag.split("_")[0]; // Exemplo: FRAMEWORK1
                if (tag.contains("_VERSION")) {
                    frameworks.put(frameworkKey, frameworks.getOrDefault(frameworkKey, "") + " v" + value);
                } else {
                    frameworks.put(frameworkKey, value);
                }
            }
        }

        // Adiciona as linguagens ao conteúdo do README
        if (!languages.isEmpty()) {
            readmeContentProjeto += "## Linguagens de Programação\n\n";
            for (Map.Entry<String, String> entry : languages.entrySet()) {
                readmeContentProjeto += "- **" + entry.getValue() + "** \n";
            }
            readmeContentProjeto += "\n";
        }

        // Adiciona os frameworks ao conteúdo do README
        if (!frameworks.isEmpty()) {
            readmeContentProjeto += "## Frameworks\n\n";
            for (Map.Entry<String, String> entry : frameworks.entrySet()) {
                readmeContentProjeto += "- **" + entry.getValue() + "**\n";
            }
            readmeContentProjeto += "\n";
        }

        // Grava o conteúdo no arquivo README.md
        try (FileWriter writer = new FileWriter(v_filePath)) {
            writer.write(readmeContentProjeto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v_filePath;
    }
}
