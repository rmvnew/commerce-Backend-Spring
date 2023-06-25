package com.delta.commerce.controller;

import com.delta.commerce.dto.filter.ClientFilter;
import com.delta.commerce.dto.request.ClientRequestDto;
import com.delta.commerce.dto.response.ClientResponseDto;
import com.delta.commerce.entity.Client;
import com.delta.commerce.service.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/client")
@Tag(name = "Client", description = "Controller para serviços de Clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(
            @RequestBody @Valid ClientRequestDto dto
    ) {
        return ResponseEntity.ok(this.clientService.createClient(dto));
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponseDto>> getAllClients(
            @RequestParam(name = "clientName", required = false) String clientName,
            @RequestParam(name = "clientCnpj", required = false) String clientCnpj,
            @RequestParam(name = "clientEmail", required = false) String clientEmail,
            @RequestParam(name = "clientResponsible", required = false) String clientResponsible,
            Pageable page
    ) {
        var filter = new ClientFilter();
        filter.setClientName(clientName);
        filter.setClientCnpj(clientCnpj);
        filter.setClientEmail(clientEmail);
        filter.setClientResponsible(clientResponsible);

        return ResponseEntity.ok(this.clientService.getAllClients(filter, page));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> findById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(this.clientService.findById(id));
    }

}
