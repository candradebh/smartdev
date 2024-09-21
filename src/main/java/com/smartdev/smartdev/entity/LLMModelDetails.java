package com.smartdev.smartdev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LLMModelDetails {

    private String parentModel;

    private String format;

    private String family;

    @ElementCollection
    @CollectionTable(name = "llm_model_families", joinColumns = @JoinColumn(name = "llm_model_id"))
    @Column(name = "family")
    private List<String> families;

    private String parameterSize;

    private String quantizationLevel;
}
