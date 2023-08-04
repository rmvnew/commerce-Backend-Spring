package com.delta.commerce.mappers;


import com.delta.commerce.dto.response.invoice.InvoiceResponseDto;
import com.delta.commerce.entity.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    InvoiceResponseDto toDto(Invoice invoice);

}
