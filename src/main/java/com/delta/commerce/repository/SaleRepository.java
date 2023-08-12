package com.delta.commerce.repository;

import com.delta.commerce.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SaleRepository extends JpaRepository<Sale, Long> {


    @Query("""
            select s from Sale s
            where s.saleCode = :code
            """)
    Sale getSaleByCode(@Param("code") String code);

    @Query("""
            select s from Sale s
            order by s.saleId desc 
            """)
    Page<Sale> getAllSales(Pageable page);

}
