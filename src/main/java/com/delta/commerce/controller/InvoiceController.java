package com.delta.commerce.controller;

import com.delta.commerce.dto.filter.InvoiceFilter;
import com.delta.commerce.dto.request.InvoiceRequestDto;
import com.delta.commerce.entity.Invoice;
import com.delta.commerce.service.InvoiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "invoice")
@Tag(name = "Invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public void createInvoice(
            @RequestBody @Valid InvoiceRequestDto dto
    ) {
        this.invoiceService.createInvoice(dto);
    }


    @GetMapping
    public ResponseEntity<Page<Invoice>> getAll(
            @RequestParam(value = "initDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate initDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(name = "invoiceNumber", required = false) String invoiceNumber,
            Pageable page
    ) {

        var filter = new InvoiceFilter();
        filter.setInitialDate(initDate);
        filter.setFinalDate(endDate);
        filter.setInvoiceNumber(invoiceNumber);

        return ResponseEntity.ok(this.invoiceService.getAllInvoice(filter, page));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Invoice> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(this.invoiceService.findById(id));
    }


    @GetMapping(value = "/number")
    public ResponseEntity<Invoice> findByNumber(
            @RequestParam(name = "invoiceNumber", required = false) String invoiceNumber
    ) {
        return ResponseEntity.ok(this.invoiceService.findByNumber(invoiceNumber));
    }


    @PutMapping("/{id}")
    public void updateInvoice(
            @RequestBody @Valid InvoiceRequestDto dto,
            @PathVariable Long id
    ) {
        this.invoiceService.updateInvoice(dto, id);
    }


}
