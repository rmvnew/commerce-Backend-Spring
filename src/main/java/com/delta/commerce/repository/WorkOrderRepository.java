package com.delta.commerce.repository;

import com.delta.commerce.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

    @Query("""
                SELECT MAX(w.workOrderNumber) FROM WorkOrder w
            """)
    Integer getLastOrderNumber();

}
