package com.delta.commerce.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Address")
@Table(name = "tb_address")
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "street")
    private String street;

    @Column(name = "home_number")
    private String homeNumber;

   @OneToOne(mappedBy = "address")
   private Client client;

    public Address(String zipcode, String state, String city,
                   String district, String street, String homeNumber,
                   Client client) {
        this.zipcode = zipcode;
        this.state = state;
        this.city = city;
        this.district = district;
        this.street = street;
        this.homeNumber = homeNumber;
        this.client = client;
    }
}
