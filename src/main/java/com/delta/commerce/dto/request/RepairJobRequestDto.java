package com.delta.commerce.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class RepairJobRequestDto {

    private Long user_id;

    private String description;

    private String reportedDefect;

    private BigDecimal price;

    private Integer estimatedDuration;

    private CustomerProductRequestDto customerProductRequestDto;

    private Set<WorkOrderLineRequestDto> workOrderLineRequestDto;

}
