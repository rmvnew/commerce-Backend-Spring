package com.delta.commerce.service;

import com.delta.commerce.dto.request.CustomerProductRequestDto;
import com.delta.commerce.entity.CustomerProduct;

public interface CustomerProductService {


    CustomerProduct createCustomProduct(CustomerProductRequestDto dto);

}
