package com.smartdev.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageRequestDTO {
    private Long projectId;

    private String message;

    private Long modelId;

}
