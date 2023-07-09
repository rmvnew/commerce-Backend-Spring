package com.delta.commerce.dto.request;

import com.delta.commerce.entity.CustomerProduct;
import lombok.Data;

import java.time.Duration;

@Data
public class RepairJobRequestDto {

    private String description;

    private String reportedDefect;

    private Double price;

    private Duration estimatedDuration;

    private CustomerProductRequestDto customerProductRequestDto;
}
