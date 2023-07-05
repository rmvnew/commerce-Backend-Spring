package com.delta.commerce.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "CustomerProduct")
@Table(name = "tb_customer_product")
@Getter
@Setter
@NoArgsConstructor
public class CustomerProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_product_id")
    private Long costumerProductId;

    @Column(name = "description")
    private String description;

    @Column(name = "model")
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    public CustomerProduct(String description, String model,
                           String serialNumber, boolean isActive,
                           LocalDateTime createAt, LocalDateTime updateAt) {
        this.description = description;
        this.model = model;
        this.serialNumber = serialNumber;
        this.isActive = isActive;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
