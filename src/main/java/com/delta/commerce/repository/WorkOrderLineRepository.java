package com.delta.commerce.repository;

import com.delta.commerce.entity.WorkOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderLineRepository extends JpaRepository<WorkOrderLine, Long> {
}
