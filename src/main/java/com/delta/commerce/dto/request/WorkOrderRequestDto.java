package com.delta.commerce.dto.request;

import com.delta.commerce.enums.WorkOrderStatus;
import lombok.Data;

import java.util.Set;

@Data
public class WorkOrderRequestDto {

    private Long client_id;

    private WorkOrderStatus workOrderStatus;

    private Set<RepairJobRequestDto> repairJobRequestDtos;

}
