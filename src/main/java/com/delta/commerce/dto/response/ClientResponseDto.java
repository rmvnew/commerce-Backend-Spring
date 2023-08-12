package com.delta.commerce.dto.response;

import com.delta.commerce.entity.Sale;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ClientResponseDto {

    private Long clientId;
    private String clientName;
    private boolean isCompany;
    private String clientCnpj;
    private String clientCpf;
    private String clientEmail;
    private String clientResponsible;
    private LocalDateTime createAt;
    private String telephone;
    private AddressResponseDto address;
//    private Set<Sale> sales;

}
