package com.delta.commerce.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity(name = "Sale")
@Table(name = "tb_sale")
@Getter
@Setter
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long saleId;

    @Column(name = "sale_code")
    private String saleCode;

    @Column(name = "total_value")
    private BigDecimal totalValue;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToOne(mappedBy = "sale", cascade = CascadeType.ALL)
    private Invoice invoice;

    @OneToMany(mappedBy = "sale")
    private Set<SaleProduct> saleProducts;

    public Sale(String saleCode, BigDecimal totalValue, LocalDateTime createAt,
                User user, Client client, Invoice invoice, Set<SaleProduct> saleProducts) {
        this.saleCode = saleCode;
        this.totalValue = totalValue;
        this.createAt = createAt;
        this.user = user;
        this.client = client;
        this.invoice = invoice;
        this.saleProducts = saleProducts;
    }
}
