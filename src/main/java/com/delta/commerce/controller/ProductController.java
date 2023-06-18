package com.delta.commerce.controller;

import com.delta.commerce.dto.request.ProductRequestDto;
import com.delta.commerce.entity.Product;
import com.delta.commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
