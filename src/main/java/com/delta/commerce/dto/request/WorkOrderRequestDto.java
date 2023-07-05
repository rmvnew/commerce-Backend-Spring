package com.delta.commerce.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class WorkOrderRequestDto {

    private LocalDateTime createAt;
    private Long user_id;
    private Long client_id;
    private Long invoice_id;
    private Map<Long, Double> products;

}
