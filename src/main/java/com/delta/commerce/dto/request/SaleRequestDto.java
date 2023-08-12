package com.delta.commerce.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class SaleRequestDto {


    private BigDecimal totalValue;
    private LocalDateTime createAt;
    private Long userId;
    private Long clientId;
    private Map<Long, Double> products;

}
