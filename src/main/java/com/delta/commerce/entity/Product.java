package com.delta.commerce.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Product")
@Table(name = "tb_product")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_barcode")
    private String productBarcode;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_ncm")
    private String productNcm;

    @Column(name = "product_cfop")
    private String productCfop;

    @Column(name = "product_unit_of_measurement")
    private String productUnitOfMeasurement;

    @Column(name = "product_quantity")
    private double productQuantity;

    @Column(name = "product_minimum_stock")
    private double productMinimumStock;

    @Column(name = "product_unit_cost")
    private BigDecimal productUnitCost;

    @Column(name = "product_unit_price")
    private BigDecimal productUnitPrice;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;


    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Category category;

    @ManyToOne
    @JoinColumn(name = "sale_id",nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Sale sale;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<InvoiceLine> invoiceLines = new HashSet<>();

    public Product(String productName, String productBarcode, String productCode,
                   String productNcm, String productCfop,
                   String productUnitOfMeasurement, double productQuantity,
                   double productMinimumStock, BigDecimal productUnitCost,
                   BigDecimal productUnitPrice, boolean isActive,
                   LocalDateTime createAt, LocalDateTime updateAt,
                   Category category, Sale sale, Set<InvoiceLine> invoiceLines) {
        this.productName = productName;
        this.productBarcode = productBarcode;
        this.productCode = productCode;
        this.productNcm = productNcm;
        this.productCfop = productCfop;
        this.productUnitOfMeasurement = productUnitOfMeasurement;
        this.productQuantity = productQuantity;
        this.productMinimumStock = productMinimumStock;
        this.productUnitCost = productUnitCost;
        this.productUnitPrice = productUnitPrice;
        this.isActive = isActive;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.category = category;
        this.sale = sale;
        this.invoiceLines = invoiceLines;
    }
}
