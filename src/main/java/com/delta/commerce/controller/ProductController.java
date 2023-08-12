package com.delta.commerce.controller;

import com.delta.commerce.dto.filter.ProductFilter;
import com.delta.commerce.dto.request.ProductRequestDto;
import com.delta.commerce.dto.response.ProductSaleResponseDto;
import com.delta.commerce.entity.Product;
import com.delta.commerce.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/product")
@Tag(name = "Product", description = "Controller para serviços de produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER_WRITE','ADMIN_WRITE')")
    public ResponseEntity<Product> createProduct(
            @RequestBody @Valid ProductRequestDto dto
    ) {
        return ResponseEntity.ok(this.productService.createProduct(dto));
    }

    @PostMapping(value = "/import")
    @PreAuthorize("hasAnyAuthority('USER_WRITE','ADMIN_WRITE')")
    public void uploadFileProducts(
            @RequestParam("file") MultipartFile file
    ) {
        this.productService.importFromCSVFile(file);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('USER_READ','ADMIN_READ')")
    public ResponseEntity<Product> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(this.productService.findById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER_READ','ADMIN_READ')")
    public ResponseEntity<Page<ProductSaleResponseDto>> getAllProduct(

            @RequestParam(name = "productName", required = false) String productName,
            @RequestParam(name = "productBarcode", required = false) String productBarcode,
            @RequestParam(name = "productCode", required = false) String productCode,
            @RequestParam(name = "productNcm", required = false) String productNcm,
            Pageable pageable
    ) {
        var filter = new ProductFilter();
        filter.setProductName(productName);
        filter.setProductBarcode(productBarcode);
        filter.setProductCode(productCode);
        filter.setProductNcm(productNcm);

        return ResponseEntity.ok(this.productService.getAllProducts(filter, pageable));
    }


    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('USER_WRITE','ADMIN_WRITE')")
    public ResponseEntity<Product> updateProduct(
            @RequestBody @Valid ProductRequestDto dto,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(this.productService.updateProduct(dto, id));
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_WRITE')")
    public void changeStatus(
            @PathVariable Long id
    ) {
        this.productService.changeStatus(id);
    }


}
