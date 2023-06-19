package com.delta.commerce.controller;

import com.delta.commerce.dto.request.CategoryRequestDto;
import com.delta.commerce.entity.Category;
import com.delta.commerce.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/category")
@Tag(name = "Category", description = "Controller para serviços de categorias")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_WRITE','USER_WRITE')")
    public ResponseEntity<Category> create(
            @RequestBody CategoryRequestDto dto
    ) {
        return ResponseEntity.ok(this.categoryService.createCategory(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_READ','USER_READ')")
    public ResponseEntity<Page<Category>> getAllCategory(
            @RequestParam(name = "name", required = false) String name,
            Pageable pageable
    ) {
        return ResponseEntity.ok(this.categoryService.getAllCategories(name, pageable));
    }

}
