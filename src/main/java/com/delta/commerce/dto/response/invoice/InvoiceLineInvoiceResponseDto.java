package com.delta.commerce.dto.response.invoice;

import lombok.Data;

@Data
public class InvoiceLineInvoiceResponseDto {

    private Long invoiceLineId;

    private ProductInvoiceResponseDto product;

    private double quantity;


}
