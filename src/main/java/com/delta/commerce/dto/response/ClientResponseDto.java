package com.delta.commerce.dto.response;

import com.delta.commerce.entity.Address;
import com.delta.commerce.entity.Sale;
import com.delta.commerce.entity.Telephone;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ClientResponseDto {

    private Long clientId;
    private String clientName;
    private String clientEmail;
    private String clientResponsible;
    private LocalDateTime createAt;
    private Set<Telephone> telephones;
    private Address address;
    private Set<Sale> sales;

}
