package com.delta.commerce.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "InvoiceLine")
@Table(name = "tb_invoice_line")
public class InvoiceLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_line_id")
    private Long invoiceLineId;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity")
    private double quantity;

    public InvoiceLine(Invoice invoice, Product product, double quantity) {
        this.invoice = invoice;
        this.product = product;
        this.quantity = quantity;
    }

}
