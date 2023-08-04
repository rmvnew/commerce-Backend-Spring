package com.delta.commerce.mappers;

import com.delta.commerce.dto.response.product.ProductResponseDto;
import com.delta.commerce.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    ProductResponseDto toDto(Product product);

}
