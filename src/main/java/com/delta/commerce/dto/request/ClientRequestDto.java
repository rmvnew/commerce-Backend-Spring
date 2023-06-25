package com.delta.commerce.dto.request;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ClientRequestDto {

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "O nome deve conter apenas letras e espaços")
    @Size(min = 5, max = 50)
    private String clientName;

    private String clientCnpj;

    private String clientCpf;

    private boolean isCompany;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "O email deve estar em um formato válido")
    private String clientEmail;

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "O nome deve conter apenas letras e espaços")
    private String clientResponsible;

    private AddressRequestDto addressRequestDto;

    private TelephoneRequestDto telephoneRequestDto;

}
