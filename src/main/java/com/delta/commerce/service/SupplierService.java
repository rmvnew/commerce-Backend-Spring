package com.delta.commerce.service;

import com.delta.commerce.dto.filter.SupplierFilter;
import com.delta.commerce.dto.request.SupplierRequestDto;
import com.delta.commerce.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupplierService {


    void createSupplier(SupplierRequestDto dto);

    Page<Supplier> getAllSupliers(SupplierFilter filter, Pageable page);


}
