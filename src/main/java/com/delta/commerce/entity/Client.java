package com.delta.commerce.entity;


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

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_cnpj")
    private String clientCnpj;

    @Column(name = "client_email")
    private String clientEmail;

    @Column(name = "client_responsible")
    private String clientResponsible;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "client")
    private Set<Telephone> telephones;

    @OneToOne
    private Address address;

    @OneToMany(mappedBy = "client")
    private Set<Sale> sales;

}
