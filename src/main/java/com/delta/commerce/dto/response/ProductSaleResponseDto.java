package com.delta.commerce.dto.response;

import com.delta.commerce.entity.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductSaleResponseDto {

    private Long productId;

    private String productName;

    private String productBarcode;

    private String productLocation;

    private String productCode;

    private String productNcm;

    private String productCfop;

    private String productUnitOfMeasurement;

    private double productQuantity;

    private double productMinimumStock;

    private BigDecimal productUnitCost;

    private BigDecimal productUnitPrice;

    private boolean isActive;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Category category;

}
