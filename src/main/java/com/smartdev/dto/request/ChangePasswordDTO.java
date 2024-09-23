package com.smartdev.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;


}

