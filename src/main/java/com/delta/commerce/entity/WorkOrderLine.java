package com.delta.commerce.entity;

import javax.persistence.*;

@Entity
@Table(name = "tb_work_order_line")
public class WorkOrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_order_line_id")
    private Long workOrderLineId;

    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "repair_job_id")
    private RepairJob repairJob;

    @ManyToOne
    @JoinColumn(name = "customer_product_id")
    private CustomerProduct customerProduct;

    @Column(name = "used")
    private boolean used; // foi usado ou retornado ao estoque?


}
