package com.delta.commerce.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProfileRequestDto {

    @NotBlank
    private String profileName;

}
