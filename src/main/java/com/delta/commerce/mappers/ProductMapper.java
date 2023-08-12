package com.delta.commerce.mappers;

import com.delta.commerce.dto.response.ProductSaleResponseDto;
import com.delta.commerce.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    ProductSaleResponseDto toDto(Product product);

}
