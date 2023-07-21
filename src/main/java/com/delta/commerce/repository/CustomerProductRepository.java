package com.delta.commerce.repository;

import com.delta.commerce.entity.CustomerProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerProductRepository extends JpaRepository<CustomerProduct, Long> {


    @Query("""
            select c from CustomerProduct c
            where 
            (:brand is null or c.brand like concat('%',:brand,'%')) and
            (:model is null or c.model like concat('%',:model,'%')) and
            (:serialNumber is null or c.serialNumber like concat('%',:serialNumber,'%')) and
            (:creatAt is null or c.createAt = :creatAt) and
            c.isActive = true
            """)
    Page<CustomerProduct> getAllCustomerProducts(
            @Param("brand") String brand,
            @Param("model") String model,
            @Param("serialNumber") String serialNumber,
            @Param("creatAt") String creatAt,
            Pageable page
    );

}
