package com.delta.commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "RepairJob")
@Table(name = "tb_repair_job")
@Getter
@Setter
@NoArgsConstructor
public class RepairJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repair_job_id")
    private Long repairJobId;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "estimated_duration")
    private Duration estimatedDuration;

    @Column(name = "technician")
    private String technician;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "repair_job_customer_product",
            joinColumns = @JoinColumn(name = "repair_job_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_product_id"))
    private Set<CustomerProduct> customerProducts;

    @ManyToMany(mappedBy = "repairJobs", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<WorkOrder> workOrders = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "repair_job_work_order_line",
            joinColumns = @JoinColumn(name = "repair_job_id"),
            inverseJoinColumns = @JoinColumn(name = "work_order_line_id"))
    private Set<WorkOrderLine> workOrderLines;


    public RepairJob(String description, Double price,
                     Duration estimatedDuration, String technician,
                     Set<CustomerProduct> customerProducts,
                     Set<WorkOrder> workOrders,
                     Set<WorkOrderLine> workOrderLines) {
        this.description = description;
        this.price = price;
        this.estimatedDuration = estimatedDuration;
        this.technician = technician;
        this.customerProducts = customerProducts;
        this.workOrders = workOrders;
        this.workOrderLines = workOrderLines;
    }
}