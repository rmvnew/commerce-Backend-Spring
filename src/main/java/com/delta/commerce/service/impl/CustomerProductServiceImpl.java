package com.delta.commerce.service.impl;

import com.delta.commerce.repository.CustomerProductRepository;
import com.delta.commerce.service.CustomerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerProductServiceImpl implements CustomerProductService {

    @Autowired
    private CustomerProductRepository customerProductRepository;

}
