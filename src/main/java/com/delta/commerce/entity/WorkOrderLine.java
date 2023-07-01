package com.delta.commerce.entity;

import javax.persistence.*;

@Entity
@Table(name = "tb_work_order_line")
public class WorkOrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private boolean used; // foi usado ou retornado ao estoque?

    // outros atributos necessários...
}
