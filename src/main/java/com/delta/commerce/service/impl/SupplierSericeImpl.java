package com.delta.commerce.service.impl;

import com.delta.commerce.dto.request.SupplierRequestDto;
import com.delta.commerce.entity.Address;
import com.delta.commerce.entity.Supplier;
import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.repository.AddressRepository;
import com.delta.commerce.repository.SupplierRepository;
import com.delta.commerce.service.SupplierService;
import com.delta.commerce.utils.ValidDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SupplierSericeImpl implements SupplierService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public void createSupplier(SupplierRequestDto dto) {

        if (!ValidDocuments.getInstance().isCNPJ(dto.getSupplierCnpj())) {
            throw new CustomException(ErrorCustom.DOCUMENT_SUPPLIER_INVALID);
        }

        var address = new Address();
        address.setZipcode(dto.getAddressRequestDto().getZipcode());
        address.setState(dto.getAddressRequestDto().getState());
        address.setCity(dto.getAddressRequestDto().getCity());
        address.setDistrict(dto.getAddressRequestDto().getDistrict());
        address.setStreet(dto.getAddressRequestDto().getStreet());
        address.setHomeNumber(dto.getAddressRequestDto().getHomeNumber());

        var addressSaved = this.addressRepository.save(address);

        var supplier = new Supplier();
        supplier.setSupplierName(dto.getSupplierName().toUpperCase());
        supplier.setSupplierCnpj(dto.getSupplierCnpj());
        supplier.setSupplierEmail(dto.getSupplierEmail());
        supplier.setSupplierTelephone(dto.getSupplierTelephone());
        supplier.setAddress(addressSaved);
        supplier.setActive(true);
        supplier.setCreateAt(LocalDateTime.now());
        supplier.setUpdateAt(LocalDateTime.now());

        this.supplierRepository.save(supplier);

    }
}
