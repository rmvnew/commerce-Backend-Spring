package com.delta.commerce.dto.request;

import lombok.Data;

@Data
public class ClientRequestDto {

    private String clientName;

    private String clientCnpj;

    private String clientEmail;

    private String clientResponsible;

    private AddressRequestDto addressRequestDto;

    private TelephoneRequestDto telephoneRequestDto;

}
