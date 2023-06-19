package com.delta.commerce.service.impl;

import com.delta.commerce.dto.request.ClientRequestDto;
import com.delta.commerce.entity.Address;
import com.delta.commerce.entity.Client;
import com.delta.commerce.repository.AddressRepository;
import com.delta.commerce.repository.ClientRepository;
import com.delta.commerce.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Client createClient(ClientRequestDto dto) {

        var address = new Address();
        address.setZipcode(dto.getAddressRequestDto().getZipcode());
        address.setState(dto.getAddressRequestDto().getState());
        address.setCity(dto.getAddressRequestDto().getCity());
        address.setDistrict(dto.getAddressRequestDto().getDistrict());
        address.setStreet(dto.getAddressRequestDto().getStreet());
        address.setHomeNumber(dto.getAddressRequestDto().getHomeNumber());

        var addressSaved = this.addressRepository.save(address);

        var client = new Client();
        client.setClientCnpj(dto.getClientCnpj());
        client.setClientEmail(dto.getClientEmail());
        client.setClientName(dto.getClientName());
        client.setClientResponsible(dto.getClientResponsible());
        client.setActive(true);
        client.setCreateAt(LocalDateTime.now());
        client.setUpdateAt(LocalDateTime.now());
        client.setAddress(addressSaved);



        return null;
    }
}
