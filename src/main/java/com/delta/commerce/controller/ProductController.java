package com.delta.commerce.controller;

import com.delta.commerce.dto.filter.ProductFilter;
import com.delta.commerce.dto.request.ProductRequestDto;
import com.delta.commerce.entity.Product;
import com.delta.commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody @Valid ProductRequestDto dto
    ) {
        return ResponseEntity.ok(this.productService.createProduct(dto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(this.productService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProduct(

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

}
