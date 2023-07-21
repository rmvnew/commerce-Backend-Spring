package com.delta.commerce.service;

import com.delta.commerce.dto.request.WorkOrderRequestDto;

public interface WorkOrderService {

    void createWorkOrder(WorkOrderRequestDto dto);

    Integer getLastOrderNumber();

}
