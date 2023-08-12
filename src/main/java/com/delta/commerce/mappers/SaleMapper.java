package com.delta.commerce.mappers;

import com.delta.commerce.dto.response.ProductSaleResponseDto;
import com.delta.commerce.dto.response.SaleResponseDto;
import com.delta.commerce.entity.Sale;
import com.delta.commerce.entity.SaleProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(source = "sale.saleProducts", target = "saleProductResponseDtos")
    SaleResponseDto toDto(Sale sale);


}




