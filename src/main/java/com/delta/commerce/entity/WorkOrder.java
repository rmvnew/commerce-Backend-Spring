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
    private Long id;

    @OneToMany(mappedBy = "workOrder")
    private Set<WorkOrderLine> workOrderLines;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "work_order_type")
    @Enumerated(EnumType.STRING)
    private WorkOrderStatus workOrderStatus;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    public WorkOrder(Set<WorkOrderLine> workOrderLines, User user,
                     Client client, WorkOrderStatus workOrderStatus,
                     boolean isActive, LocalDateTime createAt,
                     LocalDateTime updateAt) {
        this.workOrderLines = workOrderLines;
        this.user = user;
        this.client = client;
        this.workOrderStatus = workOrderStatus;
        this.isActive = isActive;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}