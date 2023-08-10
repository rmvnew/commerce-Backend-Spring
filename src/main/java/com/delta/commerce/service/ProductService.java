package com.delta.commerce.service;

import com.delta.commerce.dto.filter.ProductFilter;
import com.delta.commerce.dto.request.AddProductsRequestDto;
import com.delta.commerce.dto.request.ProductRequestDto;
import com.delta.commerce.dto.response.product.ProductResponseDto;
import com.delta.commerce.entity.Category;
import com.delta.commerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    Product createProduct(ProductRequestDto dto);

    Product findById(Long id);

    Page<ProductResponseDto> getAllProducts(ProductFilter filter, Pageable page);

    Product updateProduct(ProductRequestDto dto, Long id);

    Category getCategoryById(Long id);

    Product addProductsToStock(AddProductsRequestDto dto, String barcode);

    void changeStatus(Long id);

    void importFromCSVFile(MultipartFile file);
}
