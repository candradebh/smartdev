package com.smartdev.smartdev.dto.github;

import lombok.Data;

@Data
public class CreateRepoResponse {
    private Long id;

    private String name;

    private String full_name;

    private String html_url;
    // Outros campos conforme necessário
}
