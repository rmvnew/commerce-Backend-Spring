package com.delta.commerce.repository;

import com.delta.commerce.entity.CustomerProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerProductRepository extends JpaRepository<CustomerProduct, Long> {
}
