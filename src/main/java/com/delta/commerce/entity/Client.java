package com.delta.commerce.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "Client")
@Table(name = "tb_client")
@Getter
@Setter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "client_name",unique = true)
    private String clientName;

    @Column(name = "client_cnpj")
    private String clientCnpj;

    @Column(name = "client_cpf")
    private String clientCpf;

    @Column(name = "client_email")
    private String clientEmail;
    @Column(name = "telephone")
    private String telephone;

    @Column(name = "client_responsible")
    private String clientResponsible;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_company")
    private boolean isCompany;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<Invoice> invoices;

    @OneToOne
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Sale> sales;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<WorkOrder> workOrders;

    public Client(String clientName, String clientCnpj, String clientCpf, String clientEmail,
                  String clientResponsible, boolean isActive, boolean isCompany,
                  LocalDateTime createAt, LocalDateTime updateAt,
                  Address address, Set<Sale> sales) {

        this.clientName = clientName;
        this.clientCnpj = clientCnpj;
        this.clientCpf = clientCpf;
        this.clientEmail = clientEmail;
        this.clientResponsible = clientResponsible;
        this.isActive = isActive;
        this.isCompany = isCompany;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.address = address;
        this.sales = sales;
    }
}
