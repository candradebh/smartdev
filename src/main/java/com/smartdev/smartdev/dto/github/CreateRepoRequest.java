package com.smartdev.smartdev.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateRepoRequest {
    private String name;

    private String description;

    @JsonProperty("private")
    private boolean privateRepo;

    private boolean hasIssues;

    private boolean hasProjects;

    private boolean hasWiki;

    // Outros campos conforme necessário
}
