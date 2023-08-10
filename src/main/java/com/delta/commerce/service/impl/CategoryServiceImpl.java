package com.delta.commerce.service.impl;

import com.delta.commerce.dto.request.CategoryRequestDto;
import com.delta.commerce.entity.Category;
import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.repository.CategoryRepository;
import com.delta.commerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));
    }

    @Override
    public List<Category> getAll(String name) {

        Specification<Category> spec = (root, query, criteriaBuilder) -> {
            if (name != null && !name.trim().isEmpty()) {
                return criteriaBuilder.like(root.get("categoryName"), "%" + name + "%");
            } else {
                return criteriaBuilder.conjunction();
            }
        };

        return this.categoryRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "categoryId"));
    }

    @Override
    public Category updateCategory(CategoryRequestDto dto, Long id) {

        var category = this.findById(id);

        category.setCategoryName(dto.getCategoryName().toUpperCase());

        return this.categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        var category = this.findById(id);

        this.categoryRepository.delete(category);
    }

    @Override
    public Category findByCategoryName(String name) {
        var res = this.categoryRepository.findByName(name);

        if (res.isPresent()) {
            return res.get();
        }
        return null;
    }


}
