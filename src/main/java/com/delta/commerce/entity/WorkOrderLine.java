package com.delta.commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "WorkOrderLine")
@Table(name = "tb_work_order_line")
@Getter
@Setter
@NoArgsConstructor

public class WorkOrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_order_line_id")
    private Long workOrderLineId;

    @ManyToMany(mappedBy = "workOrderLines", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<RepairJob> repairJobs = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "used")
    private boolean used;

    public WorkOrderLine(Set<RepairJob> repairJobs,
                         Product product, boolean used) {
        this.repairJobs = repairJobs;
        this.product = product;
        this.used = used;
    }
}
