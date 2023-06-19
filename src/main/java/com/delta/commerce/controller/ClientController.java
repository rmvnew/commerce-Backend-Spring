package com.delta.commerce.controller;

import com.delta.commerce.dto.request.ClientRequestDto;
import com.delta.commerce.service.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/client")
@Tag(name = "Client", description = "Controller para serviços de Clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public void createClient(
            @RequestBody ClientRequestDto dto
            ){
        this.clientService.createClient(dto);
    }

}
