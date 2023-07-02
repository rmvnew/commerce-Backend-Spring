package com.delta.commerce.dto.request;

import com.delta.commerce.entity.InvoiceLine;
import com.delta.commerce.entity.Sale;
import com.delta.commerce.entity.Supplier;
import com.delta.commerce.enums.InvoiceTypeEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class InvoiceRequestDto {

    private String invoiceNumber;
    private LocalDate  invoiceDate;
    private InvoiceTypeEnum invoiceType;
    private LocalDate dueDate;
    private Double totalAmount;
    private Supplier supplier;
    private Boolean paid;
    private LocalDate paymentDate;
    private Set<InvoiceLine> invoiceLines = new HashSet<>();
    private Sale sale;
}
