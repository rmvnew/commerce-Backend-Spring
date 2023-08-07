package com.delta.commerce.dto.request;


import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CreateUserRequestDto {

    @NotBlank
    @Size(min = 5,max = 50)
    private String userCompleteName;

    @NotBlank
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "O email é inválido")
    private String userEmail;

    @NotBlank
    @Size(min = 6,max = 25,message = "A senha deve ter entre 6 a 25 caracteres!")
    private String userPassword;

    @Size(min = 3,max = 7)
    private String userEnrollment;

    @NotNull
    Integer profileId;

}
