package com.delta.commerce.service;

import com.delta.commerce.dto.request.ClientRequestDto;
import com.delta.commerce.entity.Client;

public interface ClientService {


    Client createClient(ClientRequestDto dto);


}
