package com.delta.commerce.dto.response.invoice;


import com.delta.commerce.dto.response.product.InvoiceLineProductResponseDto;
import com.delta.commerce.entity.Category;
import com.delta.commerce.entity.SaleProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ProductInvoiceResponseDto {

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

    private List<SaleProduct> saleProducts;


}
