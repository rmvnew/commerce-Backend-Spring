package com.delta.commerce.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SupplierRequestDto {

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "O nome deve conter apenas letras e espaços")
    @Size(min = 5, max = 50)
    private String supplierName;

    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")
    private String supplierCnpj;

    @NotBlank
    private String supplierTelephone;

    private String supplierEmail;

    private AddressRequestDto addressRequestDto;

}
