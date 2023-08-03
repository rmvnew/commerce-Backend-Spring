package com.delta.commerce.service.impl;

import com.delta.commerce.dto.filter.InvoiceFilter;
import com.delta.commerce.dto.request.InvoiceRequestDto;
import com.delta.commerce.entity.*;
import com.delta.commerce.enums.HistoricDescriptionEnum;
import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.repository.InvoiceRepository;
import com.delta.commerce.service.HistoricService;
import com.delta.commerce.service.InvoiceService;
import com.delta.commerce.service.SaleService;
import com.delta.commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoricService historicService;

    @Autowired
    private SaleService saleService;

    @Override
    public void createInvoice(InvoiceRequestDto dto) {

//        Set<InvoiceLine> invoiceLine = !dto.getInvoiceLines().isEmpty() ? dto.getInvoiceLines() : null;

        Sale sale = null;

        if (dto.getSaleCode() != null) {

            sale = this.saleService.getSaleByCode(dto.getSaleCode());

        }

        Supplier supplier = dto.getSupplier() != null ? dto.getSupplier() : null;
        Client client = dto.getClient();
        LocalDate dueDate = dto.getDueDate();
        Boolean paid = dto.getPaid();

        Invoice invoice = new Invoice(
                dto.getInvoiceNumber(),
                dto.getInvoiceDate(),
                dto.getInvoiceType(),
                dueDate,
                dto.getTotalAmount(),
                supplier,
                client,
                paid,
                dto.getPaymentDate(),
                sale
        );

        var invoiceSaved = this.invoiceRepository.save(invoice);

        this.historicService.saveHistoric(
                Invoice.class, invoiceSaved.getInvoiceId(),
                this.userService.getLoggedInUser(), HistoricDescriptionEnum.INVOICE_CREATE);

    }

    @Override
    public Page<Invoice> getAllInvoice(InvoiceFilter filter, Pageable page) {

        var initialDate = filter.getInitialDate() != null ? filter.getInitialDate() : null;
        var finalDate = filter.getFinalDate() != null ? filter.getFinalDate() : null;
        var invoiceNumber = filter.getInvoiceNumber() != null ? !filter.getInvoiceNumber().equals("") ? filter.getInvoiceNumber() : null : null;

        return this.invoiceRepository.getAllInvoices(initialDate, finalDate, invoiceNumber, page);
    }

    @Override
    public Invoice findById(Long id) {
        return this.invoiceRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));
    }

    @Override
    public Invoice findByNumber(String invoiceNumber) {
        return this.invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));
    }

    @Override
    public void updateInvoice(InvoiceRequestDto dto, Long id) {


        var invoice = this.findById(id);

        Sale sale = null;

        if (dto.getSaleCode() != null) {

            sale = this.saleService.getSaleByCode(dto.getSaleCode());

        }

        Supplier supplier = dto.getSupplier();
        Client client = dto.getClient();

        LocalDate dueDate = dto.getDueDate();
        Boolean paid = dto.getPaid();

        invoice.setInvoiceNumber(dto.getInvoiceNumber());
        invoice.setPaymentDate(dto.getPaymentDate());
        invoice.setInvoiceType(dto.getInvoiceType());
        invoice.setDueDate(dueDate);
        invoice.setTotalAmount(dto.getTotalAmount());
        invoice.setSupplier(supplier);
        invoice.setClient(client);
        invoice.setPaid(paid);
        invoice.setInvoiceDate(dto.getInvoiceDate());
        invoice.setSale(sale);

        var invoiceSaved = this.invoiceRepository.save(invoice);

        this.historicService.saveHistoric(
                Invoice.class, invoiceSaved.getInvoiceId(),
                this.userService.getLoggedInUser(), HistoricDescriptionEnum.INVOICE_UPDATE);


    }

}
