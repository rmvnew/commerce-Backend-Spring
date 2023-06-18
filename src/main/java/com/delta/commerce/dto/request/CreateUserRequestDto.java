package com.delta.commerce.dto.request;


import lombok.Data;
import lombok.ToString;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class CreateUserRequestDto {

    @NotBlank
    @Size(min = 5,max = 50)
    private String userCompleteName;

    @NotBlank
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "O email é inválido")
    private String userEmail;

    @NotBlank
    @Size(min = 4,max = 10)
    private String userPassword;

    @Size(min = 3,max = 7)
    private String userEnrollment;

    @NotEmpty
    @ToString.Exclude
    Set<Integer> profiles;

}
