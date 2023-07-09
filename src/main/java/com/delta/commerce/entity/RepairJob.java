package com.delta.commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @Column(name = "reported_defect")
    private String reportedDefect;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "estimated_duration")
    private Duration estimatedDuration;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "customer_product_id")
    private CustomerProduct customerProduct;

    @ManyToMany(mappedBy = "repairJobs", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<WorkOrder> workOrders = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "repair_job_work_order_line",
            joinColumns = @JoinColumn(name = "repair_job_id"),
            inverseJoinColumns = @JoinColumn(name = "work_order_line_id"))
    private Set<WorkOrderLine> workOrderLines;

    public RepairJob(String description, String reportedDefect, BigDecimal price,
                     Duration estimatedDuration, User user,
                     CustomerProduct customerProduct,
                     Set<WorkOrder> workOrders, Set<WorkOrderLine> workOrderLines) {
        this.description = description;
        this.reportedDefect = reportedDefect;
        this.price = price;
        this.estimatedDuration = estimatedDuration;
        this.user = user;
        this.customerProduct = customerProduct;
        this.workOrders = workOrders;
        this.workOrderLines = workOrderLines;
    }
}