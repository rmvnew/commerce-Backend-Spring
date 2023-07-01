package com.delta.commerce.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_historic_order")
public class HistoricOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;

    @Column(name = "state")
    private String state; // estado da ordem de trabalho

    @Column(name = "change_time")
    private LocalDateTime changeTime; // data/hora da mudança de estado

    // outros atributos necessários...
}