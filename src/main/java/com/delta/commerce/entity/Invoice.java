package com.delta.commerce.entity;


import com.delta.commerce.enums.InvoiceTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Invoice")
@Table(name = "tb_invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "invoice_serie")
    private String invoiceSerie;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "invoice_type")
    @Enumerated(EnumType.STRING)
    private InvoiceTypeEnum invoiceType;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "total_amount")
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = true)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = true)
    private Client client;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "invoice_note")
    private String invoiceNote;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private Set<InvoiceLine> invoiceLines = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "sale_id", nullable = true)
    private Sale sale;

    public Invoice(String invoiceNumber, String invoiceSerie, LocalDate invoiceDate,
                   InvoiceTypeEnum invoiceType, LocalDate dueDate, Double totalAmount,
                   Supplier supplier, Client client, Boolean paid, LocalDate paymentDate,
                   String invoiceNote,  Sale sale) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceSerie = invoiceSerie;
        this.invoiceDate = invoiceDate;
        this.invoiceType = invoiceType;
        this.dueDate = dueDate;
        this.totalAmount = totalAmount;
        this.supplier = supplier;
        this.client = client;
        this.paid = paid;
        this.paymentDate = paymentDate;
        this.invoiceNote = invoiceNote;
        this.sale = sale;
    }
}

