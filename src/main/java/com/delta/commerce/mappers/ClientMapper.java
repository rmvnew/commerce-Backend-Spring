package com.delta.commerce.mappers;


import com.delta.commerce.dto.response.ClientResponseDto;
import com.delta.commerce.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientResponseDto toDto(Client client);

}
