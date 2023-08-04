package com.delta.commerce.dto.response.invoice;

import com.delta.commerce.entity.Client;
import com.delta.commerce.entity.Sale;
import com.delta.commerce.entity.Supplier;
import com.delta.commerce.enums.InvoiceTypeEnum;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class InvoiceResponseDto {


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

    private Set<InvoiceLineInvoiceResponseDto> invoiceLines = new HashSet<>();

    private Sale sale;

}
