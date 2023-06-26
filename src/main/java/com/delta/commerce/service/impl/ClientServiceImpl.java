package com.delta.commerce.service.impl;

import com.delta.commerce.dto.filter.ClientFilter;
import com.delta.commerce.dto.request.ClientRequestDto;
import com.delta.commerce.dto.response.ClientResponseDto;
import com.delta.commerce.entity.Address;
import com.delta.commerce.entity.Client;
import com.delta.commerce.entity.Telephone;
import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.mappers.ClientMapper;
import com.delta.commerce.repository.AddressRepository;
import com.delta.commerce.repository.ClientRepository;
import com.delta.commerce.repository.TelephoneRepository;
import com.delta.commerce.service.ClientService;
import com.delta.commerce.utils.ValidDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public ClientResponseDto createClient(ClientRequestDto dto) {

        var isRegistered = this.clientRepository.verifyClientByName(dto.getClientName().toUpperCase());

        if (isRegistered.isPresent()) {
            throw new CustomException(ErrorCustom.CLIENT_ALREADY_EXISTS);
        }

        String cpf = null;
        String cnpj = null;

        if (dto.isCompany()) {
            if (ValidDocuments.getInstance().isCNPJ(dto.getClientCnpj())) {
                cnpj = dto.getClientCnpj();
            } else {
                throw new CustomException(ErrorCustom.DOCUMENT_COMPANY_INVALID);
            }
        } else {
            if (ValidDocuments.getInstance().isCPF(dto.getClientCpf())) {
                cpf = dto.getClientCpf();
            } else {
                throw new CustomException(ErrorCustom.DOCUMENT_CLIENT_INVALID);
            }
        }


        var address = new Address();
        address.setZipcode(dto.getAddressRequestDto().getZipcode());
        address.setState(dto.getAddressRequestDto().getState());
        address.setCity(dto.getAddressRequestDto().getCity());
        address.setDistrict(dto.getAddressRequestDto().getDistrict());
        address.setStreet(dto.getAddressRequestDto().getStreet());
        address.setHomeNumber(dto.getAddressRequestDto().getHomeNumber());

        var addressSaved = this.addressRepository.save(address);

        var client = new Client();
        client.setClientCnpj(cnpj);
        client.setClientCpf(cpf);
        client.setClientEmail(dto.getClientEmail());
        client.setClientName(dto.getClientName().toUpperCase());
        client.setClientResponsible(dto.getClientResponsible());
        client.setActive(true);
        client.setCreateAt(LocalDateTime.now());
        client.setUpdateAt(LocalDateTime.now());
        client.setAddress(addressSaved);

        var clientSaved = this.clientRepository.save(client);

        var telephones = dto.getTelephoneRequestDto().getTelephoneNumbers().stream()
                .map(number -> {
                    var telephone = new Telephone();
                    telephone.setClient(clientSaved);
                    telephone.setTelephoneNumber(number);

                    return telephone;
                }).collect(Collectors.toList());

        this.telephoneRepository.saveAll(telephones);

        return clientMapper.toDto(clientSaved);
    }

    @Override
    public Client findById(Long id) {
        return this.clientRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCustom.CLIENT_NOT_FOUND));

    }

    @Override
    public Page<ClientResponseDto> getAllClients(ClientFilter filter, Pageable page) {

        var cnpj = filter.getClientCnpj() != null ? (filter.getClientCnpj() != "" ? filter.getClientCnpj() : null) : null;

        var res = this.clientRepository.getAllClients(
                filter.getClientName(),
                cnpj,
                filter.getClientEmail(),
                filter.getClientResponsible(),
                page
        );

        var list = res.stream().map(clientMapper::toDto).collect(Collectors.toList());

        return new PageImpl<>(list, page, res.getTotalElements());

    }

    @Override
    public ClientResponseDto updateClient(ClientRequestDto dto, Long id) {

        String cpf = null;
        String cnpj = null;

        if (dto.isCompany()) {
            if (ValidDocuments.getInstance().isCNPJ(dto.getClientCnpj())) {
                cnpj = dto.getClientCnpj();
            } else {
                throw new CustomException(ErrorCustom.DOCUMENT_COMPANY_INVALID);
            }
        } else {
            if (ValidDocuments.getInstance().isCPF(dto.getClientCpf())) {
                cpf = dto.getClientCpf();
            } else {
                throw new CustomException(ErrorCustom.DOCUMENT_CLIENT_INVALID);
            }
        }

        var client = this.findById(id);

        client.getAddress().setZipcode(dto.getAddressRequestDto().getZipcode());
        client.getAddress().setState(dto.getAddressRequestDto().getState());
        client.getAddress().setCity(dto.getAddressRequestDto().getCity());
        client.getAddress().setDistrict(dto.getAddressRequestDto().getDistrict());
        client.getAddress().setStreet(dto.getAddressRequestDto().getStreet());
        client.getAddress().setHomeNumber(dto.getAddressRequestDto().getHomeNumber());
        client.setClientCnpj(cnpj);
        client.setClientCpf(cpf);
        client.setClientEmail(dto.getClientEmail());
        client.setClientName(dto.getClientName().toUpperCase());
        client.setClientResponsible(dto.getClientResponsible());
        client.setActive(true);
        client.setCreateAt(LocalDateTime.now());
        client.setUpdateAt(LocalDateTime.now());

        var clientSaved = this.clientRepository.save(client);

        var telephones = dto.getTelephoneRequestDto().getTelephoneNumbers().stream()
                .map(number -> {
                    var telephone = new Telephone();
                    telephone.setClient(clientSaved);
                    telephone.setTelephoneNumber(number);

                    return telephone;
                }).collect(Collectors.toList());

        this.telephoneRepository.saveAll(telephones);

        return clientMapper.toDto(clientSaved);
    }
}
