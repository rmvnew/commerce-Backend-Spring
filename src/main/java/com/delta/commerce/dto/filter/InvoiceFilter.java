package com.delta.commerce.dto.filter;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceFilter {

    private LocalDate initialDate;
    private LocalDate finalDate;
    private String invoiceNumber;

}
