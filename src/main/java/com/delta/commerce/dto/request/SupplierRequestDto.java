package com.delta.commerce.dto.request;

import lombok.Data;

@Data
public class SupplierRequestDto {

    private String supplierName;

    private String supplierTelephone;

    private String supplierEmail;

    private AddressRequestDto addressRequestDto;

}
