package com.delta.commerce.service.impl;

import com.delta.commerce.dto.request.CategoryRequestDto;
import com.delta.commerce.entity.Category;
import com.delta.commerce.repository.CategoryRepository;
import com.delta.commerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryRequestDto dto) {
        return this.categoryRepository.save(new Category(dto.getCategoryName().toUpperCase()));
    }

    @Override
    public Page<Category> getAllCategories(String name, Pageable pageable) {
        return this.categoryRepository.getAllCategory(name, pageable);
    }


}
