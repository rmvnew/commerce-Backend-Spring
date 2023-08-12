package com.delta.commerce.controller;

import com.delta.commerce.dto.request.SaleRequestDto;
import com.delta.commerce.dto.response.SaleResponseDto;
import com.delta.commerce.entity.Sale;
import com.delta.commerce.service.SaleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/sale")
@Tag(name = "Sale", description = "Controller para serviços de venda")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER_WRITE','ADMIN_WRITE')")
    public void createSale(
            @RequestBody @Valid SaleRequestDto dto
    ) {
        this.saleService.createSale(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER_READ','ADMIN_READ')")
    public ResponseEntity<Page<SaleResponseDto>> getAll(
            Pageable page
    ) {
        return ResponseEntity.ok(this.saleService.getAllSales(page));
    }

}
