package com.delta.commerce.dto.response;

import lombok.Data;

@Data
public class SaleProductResponseDto {

    private Long id;

    private ProductSaleResponseDto product;

    private double quantity;

}
