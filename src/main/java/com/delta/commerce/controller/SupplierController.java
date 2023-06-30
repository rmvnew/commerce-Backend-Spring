package com.delta.commerce.controller;

import com.delta.commerce.dto.filter.SupplierFilter;
import com.delta.commerce.dto.request.SupplierRequestDto;
import com.delta.commerce.entity.Supplier;
import com.delta.commerce.service.SupplierService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER_READ','ADMIN_READ')")
    public ResponseEntity<Page<Supplier>> getAllSupplier(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "cnpj", required = false) String cnpj,
            Pageable page
    ) {

        var filter = new SupplierFilter();
        filter.setSupplierName(name);
        filter.setSupplierCnpj(cnpj);

        return ResponseEntity.ok(this.supplierService.getAllSupliers(filter, page));
    }


    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('USER_READ','ADMIN_READ')")
    public Supplier findById(
            @PathVariable Long id
    ) {
        return this.supplierService.findById(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('USER_WRITE','ADMIN_WRITE')")
    public void updateSupplier(
            @RequestBody SupplierRequestDto dto,
            @PathVariable Long id
    ) {
        this.supplierService.updateSupplier(dto, id);
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    public void changeStatus(
            @PathVariable Long id
    ) {
        this.supplierService.changeStatus(id);
    }

}
