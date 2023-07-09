package com.delta.commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private String brand;

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

    @OneToMany(mappedBy = "customerProduct", fetch = FetchType.LAZY)
    private Set<RepairJob> repairJobs = new HashSet<>();

    public CustomerProduct(String brand, String model,
                           String serialNumber, boolean isActive,
                           LocalDateTime createAt,
                           LocalDateTime updateAt, Set<RepairJob> repairJobs) {
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.isActive = isActive;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.repairJobs = repairJobs;
    }
}
