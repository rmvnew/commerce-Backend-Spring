package com.delta.commerce.service;

import com.delta.commerce.dto.request.SaleRequestDto;
import com.delta.commerce.dto.response.SaleResponseDto;
import com.delta.commerce.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleService {


    void createSale(SaleRequestDto sale);

    Sale getSaleByCode(String code);

    Page<SaleResponseDto> getAllSales(Pageable page);

}
