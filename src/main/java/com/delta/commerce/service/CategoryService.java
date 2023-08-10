package com.delta.commerce.service;

import com.delta.commerce.dto.request.CategoryRequestDto;
import com.delta.commerce.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    Category createCategory(CategoryRequestDto dto);

    Page<Category> getAllCategories(String name, Pageable pageable);

    Category findById(Long id);

    List<Category> getAll(String name);

    Category updateCategory(CategoryRequestDto dto, Long id);

    void delete(Long id);

    Category findByCategoryName(String name);

}
