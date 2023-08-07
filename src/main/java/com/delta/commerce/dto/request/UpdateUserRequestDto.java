package com.delta.commerce.dto.request;


import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.Set;

@Data
public class UpdateUserRequestDto {

    @NotBlank
    @Size(min = 5,max = 50)
    private String userCompleteName;

    @NotBlank
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "O email é inválido")
    private String userEmail;

    @Size(min = 3,max = 7)
    private String userEnrollment;

    @NotNull
    Integer profileId;

}
