package com.delta.commerce.service.impl;

import com.delta.commerce.dto.request.ProductRequestDto;
import com.delta.commerce.entity.Product;
import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.repository.CategoryRepository;
import com.delta.commerce.repository.ProductRepository;
import com.delta.commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Product createProduct(ProductRequestDto dto) {

        var isRegistered = this.productRepository
                .findByProductNameAndBarcode(dto.getProductName().toUpperCase(), dto.getProductBarcode());

        if (isRegistered.isPresent()) {
            throw new CustomException(ErrorCustom.PRODUCT_ALREADY_EXISTS);
        }

        var category = this.categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCustom.CATEGORY_NOT_FOUND));

        var product = new Product();
        product.setCreateAt(LocalDateTime.now());
        product.setUpdateAt(LocalDateTime.now());
        product.setActive(true);
        product.setProductName(dto.getProductName().toUpperCase());
        product.setProductBarcode(dto.getProductBarcode());
        product.setProductCode(dto.getProductCode());
        product.setProductNcm(dto.getProductNcm());
        product.setProductCfop(dto.getProductCfop());
        product.setProductUnitOfMeasurement(dto.getProductUnitOfMeasurement());
        product.setProductQuantity(dto.getProductQuantity());
        product.setProductMinimumStock(dto.getProductMinimumStock());
        product.setProductUnitCost(dto.getProductUnitCost());
        product.setProductUnitPrice(dto.getProductUnitPrice());
        product.setCategory(category);

        return this.productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));
    }


}
