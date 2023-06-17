package com.delta.commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "Transaction")
@Table(name = "tb_transaction")
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "transaction_name")
    private String transactionName;

    @ManyToMany(mappedBy = "transactions",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Profile> profiles = new HashSet<>();

}
