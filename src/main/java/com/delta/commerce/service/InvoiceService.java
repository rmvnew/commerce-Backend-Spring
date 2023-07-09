package com.delta.commerce.service;

import com.delta.commerce.dto.filter.InvoiceFilter;
import com.delta.commerce.dto.request.InvoiceRequestDto;
import com.delta.commerce.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceService {

    void createInvoice(InvoiceRequestDto dto);

    Page<Invoice> getAllInvoice(InvoiceFilter filter, Pageable page);

    Invoice findById(Long id);

    Invoice findByNumber(String invoiceNumber);

    void updateInvoice(InvoiceRequestDto dto,Long id);

}
