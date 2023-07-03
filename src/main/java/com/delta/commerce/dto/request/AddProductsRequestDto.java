package com.delta.commerce.dto.request;

import lombok.Data;

@Data
public class AddProductsRequestDto {

    private String invoiceNumber;
    private Double quantity;

}
