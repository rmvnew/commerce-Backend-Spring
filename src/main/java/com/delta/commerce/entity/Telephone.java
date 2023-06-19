package com.delta.commerce.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Telephone")
@Table(name = "tb_telephone")
@Getter
@Setter
@NoArgsConstructor
public class Telephone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "telephone_id")
    private Long telephoneId;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @ManyToOne
    @JoinColumn(name = "client_id",nullable = false)
    private Client client;

}
