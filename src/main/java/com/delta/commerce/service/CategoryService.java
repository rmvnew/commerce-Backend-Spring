package com.delta.commerce.service;

import com.delta.commerce.dto.request.CategoryRequestDto;
import com.delta.commerce.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Category createCategory(CategoryRequestDto dto);

    Page<Category> getAllCategories(String name, Pageable pageable);

}
