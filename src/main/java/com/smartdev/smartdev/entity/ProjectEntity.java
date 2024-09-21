package com.smartdev.smartdev.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectEntity extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    // diretorio de desenvolvimento local
    private String workspacePath;

    // diretorio do git
    private String gitPath;

    @Lob
    private String description;

    private Boolean isActive;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<FeatureEntity> features = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TaskEntity> tasks;


    public String getPathProject() {
        return this.getWorkspacePath() + (this.getWorkspacePath() != null && this.getWorkspacePath().endsWith("/") ? this.getName() : "/" + this.getName());
    }

    public String getDefaultIntro() {
        String intro = String.format("Nome do Projeto: %s \n" + //
                        "Caminho do Projeto no sistema: %s \n" + //
                        "Descrição: " + "%s \n" + //
                        "Repositório do Git: %s .\n", //
                this.getName(), //
                this.getPathProject(), //
                this.getDescription(), //
                this.getGitPath());

        Map<String, String> languages = new HashMap<>();
        Map<String, String> frameworks = new HashMap<>();

        for (FeatureEntity feature : this.getFeatures()) {
            String tag = "";//feature.getTag();
            String value = "";//feature.getFeatureValue();

            if (tag.startsWith("LANGUAGE")) {
                String languageKey = tag.split("_")[0]; // Exemplo: LANGUAGE1
                if (tag.contains("_VERSION")) {
                    languages.put(languageKey, languages.getOrDefault(languageKey, "") + " versão: " + value);
                } else {
                    languages.put(languageKey, value);
                }
            } else if (tag.startsWith("FRAMEWORK")) {
                String frameworkKey = tag.split("_")[0]; // Exemplo: FRAMEWORK1
                if (tag.contains("_VERSION")) {
                    frameworks.put(frameworkKey, frameworks.getOrDefault(frameworkKey, "") + " versão: " + value);
                } else {
                    frameworks.put(frameworkKey, value);
                }
            }
        }

        // Adiciona as linguagens ao conteúdo do README
        if (!languages.isEmpty()) {
            String v_linguagens = "Linguagens de Programação utilizadas são: ";
            for (Map.Entry<String, String> entry : languages.entrySet()) {
                v_linguagens += v_linguagens.endsWith(",") ? " " + entry.getValue() : ", " + entry.getValue();
            }
            intro += v_linguagens + "\n";
        }

        // Adiciona os frameworks ao conteúdo do README
        if (!frameworks.isEmpty()) {
            String v_frameworks = "Frameworks utilizados: ";
            for (Map.Entry<String, String> entry : frameworks.entrySet()) {
                v_frameworks += v_frameworks.endsWith(",") ? " " + entry.getValue() : ", " + entry.getValue();
            }
            intro += v_frameworks + "\n";
        }

        return intro;
    }

}
