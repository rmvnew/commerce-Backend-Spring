package com.delta.commerce.controller;

import com.delta.commerce.dto.request.SupplierRequestDto;
import com.delta.commerce.service.SupplierService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "supplier")
@Tag(name = "Supplier", description = "Controller para serviços de fornecedor")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER_WRITE','ADMIN_WRITE')")
    public void createSupplier(
            @RequestBody SupplierRequestDto dto
    ) {
        this.supplierService.createSupplier(dto);
    }


}
