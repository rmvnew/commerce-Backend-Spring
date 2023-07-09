package com.delta.commerce.repository;

import com.delta.commerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("""
            select p from Product p
            where p.isActive = true
            and (p.productName = :name or p.productBarcode = :barcode)
            order by p.productName
            """)
    Optional<Product> findByProductNameAndBarcode(@Param("name") String name, @Param("barcode") String barcode);


    @Query("""
            select p from Product p
            where 
            (:productName is null or p.productName like concat('%',:productName,'%')) AND
            (:productBarcode is null or p.productBarcode = :productBarcode) AND
            (:productCode is null or p.productCode = :productCode) AND
            (:productNcm is null or p.productNcm = :productNcm) AND 
            p.isActive = true
            """)
    Page<Product> getAllProduct(
            @Param("productName") String productName,
            @Param("productBarcode") String productBarcode,
            @Param("productCode") String productCode,
            @Param("productNcm") String productNcm,
            Pageable pageable
    );



    @Query("""
            select p from Product p
            where p.isActive = true AND
            p.productBarcode = :barcode
                        """)
    Optional<Product> findByProductBarcode(@Param("barcode") String barcode);

}
