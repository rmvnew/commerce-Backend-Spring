package com.delta.commerce.controller;

import com.delta.commerce.dto.request.SaleRequestDto;
import com.delta.commerce.service.SaleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
