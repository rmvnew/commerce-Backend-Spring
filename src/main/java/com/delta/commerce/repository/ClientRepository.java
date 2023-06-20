package com.delta.commerce.repository;

import com.delta.commerce.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("""
            select c from Client c
            where c.isActive = true AND
            (:clientName is null or c.clientName like concat('%',:clientName,'%')) AND
            (:clientCnpj is null or c.clientCnpj = :clientCnpj) AND
            (:clientEmail is null or c.clientEmail like concat('%',:clientEmail,'%')) AND
            (:clientResponsible is null or c.clientResponsible like concat('%',:clientResponsible,'%'))
            """)
    Page<Client> getAllClients(
            @Param("clientName") String clientName,
            @Param("clientCnpj") String clientCnpj,
            @Param("clientEmail") String clientEmail,
            @Param("clientResponsible") String clientResponsible,
            Pageable page
    );

}
