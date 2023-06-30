package com.delta.commerce.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "supplier")
    private Set<Invoice> invoices;

    public Supplier(String supplierName, String supplierCnpj,
                    String supplierTelephone, String supplierEmail,
                    boolean isActive, LocalDateTime createAt,
                    LocalDateTime updateAt, Address address, Set<Invoice> invoices) {
        this.supplierName = supplierName;
        this.supplierCnpj = supplierCnpj;
        this.supplierTelephone = supplierTelephone;
        this.supplierEmail = supplierEmail;
        this.isActive = isActive;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.address = address;
        this.invoices = invoices;
    }
}
