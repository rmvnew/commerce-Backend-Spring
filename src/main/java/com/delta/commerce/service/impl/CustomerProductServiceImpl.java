package com.delta.commerce.service.impl;

import com.delta.commerce.dto.request.CustomerProductRequestDto;
import com.delta.commerce.entity.CustomerProduct;
import com.delta.commerce.repository.CustomerProductRepository;
import com.delta.commerce.service.CustomerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerProductServiceImpl implements CustomerProductService {

    @Autowired
    private CustomerProductRepository customerProductRepository;

    @Override
    public CustomerProduct createCustomProduct(CustomerProductRequestDto dto) {

        var customProduct = new CustomerProduct();
        customProduct.setCreateAt(LocalDateTime.now());
        customProduct.setUpdateAt(LocalDateTime.now());
        customProduct.setModel(dto.getModel());
        customProduct.setBrand(dto.getBrand());
        customProduct.setActive(true);
        customProduct.setSerialNumber(dto.getSerialNumber());

        return customerProductRepository.save(customProduct);
    }
}
