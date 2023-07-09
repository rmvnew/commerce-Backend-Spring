package com.delta.commerce.repository;

import com.delta.commerce.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {


    @Query("""
        select s from Supplier s
        where s.isActive = true AND
        (:supplierName is null or s.supplierName like concat('%', :supplierName, '%')) AND
        (:supplierCnpj is null or s.supplierCnpj like concat('%',:supplierCnpj,'%') ) 
       """)
    Page<Supplier> getAllSuppliers(
            @Param("supplierName") String supplierName,
            @Param("supplierCnpj") String supplierCnpj,
            Pageable page
    );




}
