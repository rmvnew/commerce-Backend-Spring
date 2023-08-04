package com.delta.commerce.dto.response.product;

import lombok.Data;

@Data
public class InvoiceLineProductResponseDto {

    private Long invoiceLineId;

    private InvoiceProductResponseDto invoice;

    private double quantity;


}
