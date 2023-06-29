package com.delta.commerce.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "Supplier")
@Table(name = "tb_supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long supplier_id;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supplier_cnpj")
    private String supplierCnpj;

    @Column(name = "supplier_telephone")
    private String supplierTelephone;

    @Column(name = "supplier_email")
    private String supplierEmail;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @OneToOne
    private Address address;


}
