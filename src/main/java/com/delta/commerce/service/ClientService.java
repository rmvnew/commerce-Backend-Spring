package com.delta.commerce.service;

import com.delta.commerce.dto.filter.ClientFilter;
import com.delta.commerce.dto.request.ClientRequestDto;
import com.delta.commerce.dto.response.ClientResponseDto;
import com.delta.commerce.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {


    ClientResponseDto createClient(ClientRequestDto dto);

    Client findById(Long id);

    Page<ClientResponseDto> getAllClients(ClientFilter filter, Pageable page);

    ClientResponseDto updateClient(ClientRequestDto dto, Long id);


}
