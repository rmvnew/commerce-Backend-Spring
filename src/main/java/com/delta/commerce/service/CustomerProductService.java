package com.delta.commerce.service;

import com.delta.commerce.dto.filter.CustomProductFilter;
import com.delta.commerce.dto.request.CustomerProductRequestDto;
import com.delta.commerce.entity.CustomerProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerProductService {


    CustomerProduct createCustomProduct(CustomerProductRequestDto dto);

    CustomerProduct findById(Long id);

    Page<CustomerProduct> findAllCustomProducts(CustomProductFilter filter, Pageable page);

    CustomerProduct updateCustomProduct(CustomerProductRequestDto dto, Long id);

    void changeStatus(Long id);

}
