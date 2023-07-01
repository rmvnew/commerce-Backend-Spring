package com.delta.commerce.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tb_work_order")
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "workOrder")
    private Set<WorkOrderLine> workOrderLines;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}