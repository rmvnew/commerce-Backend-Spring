package com.delta.commerce.entity;

import com.delta.commerce.enums.WorkOrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "WorkOrder")
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_work_order")
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_order_id")
    private Long workOrderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "work_order_status")
    @Enumerated(EnumType.STRING)
    private WorkOrderStatus workOrderStatus;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "work_order_repair_job",
            joinColumns = @JoinColumn(name = "work_order_id"),
            inverseJoinColumns = @JoinColumn(name = "repair_job_id"))
    private Set<RepairJob> repairJobs;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    public WorkOrder(User user, Client client,
                     WorkOrderStatus workOrderStatus,
                     boolean isActive, LocalDateTime createAt,
                     LocalDateTime updateAt, Set<RepairJob> repairJobs) {
        this.user = user;
        this.client = client;
        this.workOrderStatus = workOrderStatus;
        this.isActive = isActive;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.repairJobs = repairJobs;
    }
}