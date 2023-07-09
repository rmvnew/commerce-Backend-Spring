package com.delta.commerce.dto.request;

import lombok.Data;

@Data
public class WorkOrderLineRequestDto {

    private Long product_id;

    private Double quantity;

    private Double used;

    private Double returned;

}
