package com.delta.commerce.entity;


import com.delta.commerce.enums.InvoiceTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @Column(name = "invoice_date")
    private LocalDateTime invoiceDate;

    @Column(name = "invoice_type")
    @Enumerated(EnumType.STRING)
    private InvoiceTypeEnum invoiceType;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "total_amount")
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private Set<InvoiceLine> invoiceLines = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "sale_id", nullable = true)
    private Sale sale;

    public Invoice(String invoiceNumber, LocalDateTime invoiceDate,
                   InvoiceTypeEnum invoiceType, LocalDateTime dueDate,
                   Double totalAmount, Supplier supplier, Boolean paid,
                   LocalDateTime paymentDate, Set<InvoiceLine> invoiceLines) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.invoiceType = invoiceType;
        this.dueDate = dueDate;
        this.totalAmount = totalAmount;
        this.supplier = supplier;
        this.paid = paid;
        this.paymentDate = paymentDate;
        this.invoiceLines = invoiceLines;
    }
}

