package com.delta.commerce.service;

import com.delta.commerce.dto.request.ProductRequestDto;
import com.delta.commerce.entity.Product;

public interface ProductService {

    Product createProduct(ProductRequestDto dto);

    Product findById(Long id);

}
