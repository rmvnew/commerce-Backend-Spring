package com.delta.commerce.mappers;

import com.delta.commerce.dto.response.SaleResponseDto;
import com.delta.commerce.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(source = "sale.saleProducts", target = "saleProductResponseDtos")
    @Mapping(source = "sale.user", target = "userResponse")
    @Mapping(source = "sale.client", target = "clientResponseDto")
    SaleResponseDto toDto(Sale sale);


}




