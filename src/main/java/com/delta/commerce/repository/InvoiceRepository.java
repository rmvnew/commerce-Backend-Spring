package com.delta.commerce.repository;

import com.delta.commerce.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {


    @Query("""
            select i from Invoice i
            where 
            (:invoiceNumber is null or i.invoiceNumber = :invoiceNumber)
            AND (:initialDate is null or i.invoiceDate >= :initialDate)
            AND (:finalDate is null or i.invoiceDate <= :finalDate)
                        """)
    Page<Invoice> getAllInvoices(
            @Param("initialDate") LocalDate initialDate,
            @Param("finalDate") LocalDate finalDate,
            @Param("invoiceNumber") String invoiceNumber,
            Pageable page
    );

    Optional<Invoice> findByInvoiceNumber(@Param("number") String number);

}
