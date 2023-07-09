package com.delta.commerce.dto.filter;

import lombok.Data;

@Data
public class ClientFilter {

    private String clientName;
    private String clientCnpj;
    private String clientCpf;
    private String clientEmail;
    private String clientResponsible;

}
