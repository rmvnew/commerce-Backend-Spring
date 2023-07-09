package com.delta.commerce.dto.request;

import lombok.Data;

@Data
public class RepairJobRequestDto {

    private String description;

    private String reportedDefect;

    private Double price;

    private Integer estimatedDuration;

    private CustomerProductRequestDto customerProductRequestDto;
}
