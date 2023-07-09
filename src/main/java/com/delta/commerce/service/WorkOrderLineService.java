package com.delta.commerce.service;

import com.delta.commerce.dto.request.WorkOrderLineRequestDto;
import com.delta.commerce.entity.WorkOrderLine;

public interface WorkOrderLineService {

    WorkOrderLine createWorkOrderLine(WorkOrderLineRequestDto dto);

}
