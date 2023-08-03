package com.delta.commerce.service;

import com.delta.commerce.dto.request.SaleRequestDto;
import com.delta.commerce.entity.Sale;

public interface SaleService {


    void createSale(SaleRequestDto sale);

    Sale getSaleByCode(String code);

}
