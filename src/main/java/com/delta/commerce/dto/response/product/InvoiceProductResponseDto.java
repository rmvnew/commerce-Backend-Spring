package com.delta.commerce.dto.response.product;


import com.delta.commerce.entity.Client;
import com.delta.commerce.entity.Sale;
import com.delta.commerce.entity.Supplier;
import com.delta.commerce.enums.InvoiceTypeEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceProductResponseDto {

    private Long invoiceId;

    private String invoiceNumber;

    private String invoiceSerie;

    private LocalDate invoiceDate;

    private InvoiceTypeEnum invoiceType;

    private LocalDate dueDate;

    private Double totalAmount;

    private Supplier supplier;

    private Client client;

    private Boolean paid;

    private LocalDate paymentDate;

    private String invoiceNote;

    private Sale sale;

}
