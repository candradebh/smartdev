package com.smartdev.smartdev.dto;

import com.smartdev.smartdev.entity.LLMModelEntity;

import java.util.List;

public class LLMModelResponse {

    private List<LLMModelEntity> models;

    // Getters and Setters
    public List<LLMModelEntity> getModels() {
        return models;
    }

    public void setModels(List<LLMModelEntity> models) {
        this.models = models;
    }
}
