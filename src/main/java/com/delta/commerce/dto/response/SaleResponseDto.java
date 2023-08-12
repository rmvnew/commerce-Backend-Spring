package com.delta.commerce.dto.response;

import com.delta.commerce.dto.response.invoice.InvoiceResponseDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class SaleResponseDto {

    private Long saleId;

    private String saleCode;

    private BigDecimal totalValue;

    private LocalDateTime createAt;

    private UserResponse userResponse;

    private ClientResponseDto clientResponseDto;

    private InvoiceResponseDto invoiceResponseDto;

    private Set<SaleProductResponseDto> saleProductResponseDtos;

}
