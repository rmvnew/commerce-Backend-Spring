package com.delta.commerce.service.impl;

import com.delta.commerce.dto.filter.ClientFilter;
import com.delta.commerce.dto.request.ClientRequestDto;
import com.delta.commerce.entity.Address;
import com.delta.commerce.entity.Client;
import com.delta.commerce.entity.Telephone;
import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.repository.AddressRepository;
import com.delta.commerce.repository.ClientRepository;
import com.delta.commerce.repository.TelephoneRepository;
import com.delta.commerce.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TelephoneRepository telephoneRepository;

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

        var clientSaved = this.clientRepository.save(client);

        var telephones = dto.getTelephoneRequestDto().getTelephoneNumbers().stream()
                .map(number ->{
                    var telephone = new Telephone();
                    telephone.setClient(clientSaved);
                    telephone.setTelephoneNumber(number);

                    return telephone;
                }).collect(Collectors.toList());

        this.telephoneRepository.saveAll(telephones);

        return clientSaved;
    }

    @Override
    public Client findById(Long id) {
        return this.clientRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCustom.CLIENT_NOT_FOUND));
    }

    @Override
    public Page<Client> getAllClients(ClientFilter filter, Pageable page) {

        var cnpj = filter.getClientCnpj() != null ? (filter.getClientCnpj() != "" ? filter.getClientCnpj() : null) : null;

        return this.clientRepository.getAllClients(
                filter.getClientName(),
                cnpj,
                filter.getClientEmail(),
                filter.getClientResponsible(),
                page
        );
    }
}
