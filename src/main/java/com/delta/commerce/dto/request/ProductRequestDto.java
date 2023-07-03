package com.delta.commerce.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class ProductRequestDto {

    @NotBlank
    @Size(min = 5, max = 50)
    private String productName;

    @Pattern(regexp = "\\d+",message = "only numbers are allowed")
    private String productBarcode;

    @Pattern(regexp = "\\d+",message = "only numbers are allowed")
    private String productCode;

    private String productNcm;

    private String productCfop;

    @NotBlank
    private String productUnitOfMeasurement;

    private Double productQuantity;

    private Double productMinimumStock;

    private BigDecimal productUnitCost;

    private BigDecimal productUnitPrice;

    private Long categoryId;

    private String invoiceNumber;

}
