package com.delta.commerce.repository;

import com.delta.commerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {


    @Query("""
            select p from Product p
            where p.isActive = true
            and (p.productName = :name or p.productBarcode = :barcode)
            order by p.productName
            """)
    Optional<Product> findByProductNameAndBarcode(@Param("name") String name,@Param("barcode") String barcode);




}
