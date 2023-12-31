package com.delta.commerce.service.impl;

import com.delta.commerce.dto.filter.ClientFilter;
import com.delta.commerce.dto.request.ClientRequestDto;
import com.delta.commerce.dto.response.ClientResponseDto;
import com.delta.commerce.entity.Address;
import com.delta.commerce.entity.Client;
import com.delta.commerce.enums.HistoricDescriptionEnum;
import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.mappers.ClientMapper;
import com.delta.commerce.repository.AddressRepository;
import com.delta.commerce.repository.ClientRepository;
import com.delta.commerce.service.ClientService;
import com.delta.commerce.service.HistoricService;
import com.delta.commerce.service.UserService;
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
    private ClientMapper clientMapper;

    @Autowired
    private HistoricService historicService;

    @Autowired
    private UserService userService;

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
                cnpj = dto.getClientCnpj().replaceAll("[^0-9]", "");
                ;
            } else {
                throw new CustomException(ErrorCustom.DOCUMENT_COMPANY_INVALID);
            }
        } else {
            if (ValidDocuments.getInstance().isCPF(dto.getClientCpf())) {
                cpf = dto.getClientCpf().replaceAll("[^0-9]", "");
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
        client.setTelephone(dto.getTelephone());
        client.setActive(true);
        client.setCreateAt(LocalDateTime.now());
        client.setUpdateAt(LocalDateTime.now());
        client.setAddress(addressSaved);

        var clientSaved = this.clientRepository.save(client);

        this.historicService.saveHistoric(
                Client.class, clientSaved.getClientId(), this.userService.getLoggedInUser(),
                HistoricDescriptionEnum.CLIENT_CREATE);

        return clientMapper.toDto(clientSaved);
    }

    @Override
    public Client findById(Long id) {
        return this.clientRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCustom.CLIENT_NOT_FOUND));

    }

    @Override
    public Page<ClientResponseDto> getAllClients(ClientFilter filter, Pageable page) {

        var cnpj = filter.getClientCnpj() != null ? (filter.getClientCnpj() != "" ? filter.getClientCnpj().replaceAll("[^0-9]", "") : null) : null;
        var cpf = filter.getClientCpf() != null ? (filter.getClientCpf() != "" ? filter.getClientCpf().replaceAll("[^0-9]", "") : null) : null;

        if (cnpj != null && !ValidDocuments.getInstance().isCNPJ(cnpj)) {
            throw new IllegalArgumentException("CNPJ anválido: " + cnpj);
        }

        if (cpf != null && !ValidDocuments.getInstance().isCPF(cpf)) {
            throw new IllegalArgumentException("CPF anválido: " + cpf);
        }

        var res = this.clientRepository.getAllClients(
                filter.getClientName(),
                cnpj,
                cpf,
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
                cnpj = dto.getClientCnpj().replaceAll("[^0-9]", "");
            } else {
                throw new CustomException(ErrorCustom.DOCUMENT_COMPANY_INVALID);
            }
        } else {
            if (ValidDocuments.getInstance().isCPF(dto.getClientCpf())) {
                cpf = dto.getClientCpf().replaceAll("[^0-9]", "");
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
        client.setTelephone(dto.getTelephone());
        client.setActive(true);
        client.setUpdateAt(LocalDateTime.now());

        var clientSaved = this.clientRepository.save(client);

        this.historicService.saveHistoric(
                Client.class, clientSaved.getClientId(), this.userService.getLoggedInUser(),
                HistoricDescriptionEnum.CLIENT_UPDATE);

        return clientMapper.toDto(clientSaved);
    }

    @Override
    public void changeStatus(Long id) {
        var client = this.findById(id);
        client.setActive(false);
        var clientSaved = this.clientRepository.save(client);

        this.historicService.saveHistoric(
                Client.class, clientSaved.getClientId(), this.userService.getLoggedInUser(),
                HistoricDescriptionEnum.CLIENT_CREATE);
    }
}
