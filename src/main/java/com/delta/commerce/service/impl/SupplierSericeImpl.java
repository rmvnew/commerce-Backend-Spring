package com.delta.commerce.service.impl;

import com.delta.commerce.dto.filter.SupplierFilter;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        var isValid = ValidDocuments.getInstance().isCNPJ(dto.getSupplierCnpj());

        if (!isValid) {
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
        var cnpj = dto.getSupplierCnpj().replaceAll("[^0-9]", "");

        var supplier = new Supplier();
        supplier.setSupplierName(dto.getSupplierName().toUpperCase());
        supplier.setSupplierCnpj(cnpj);
        supplier.setSupplierEmail(dto.getSupplierEmail());
        supplier.setSupplierTelephone(dto.getSupplierTelephone());
        supplier.setAddress(addressSaved);
        supplier.setActive(true);
        supplier.setCreateAt(LocalDateTime.now());
        supplier.setUpdateAt(LocalDateTime.now());

        this.supplierRepository.save(supplier);

    }

    @Override
    public Page<Supplier> getAllSupliers(SupplierFilter filter, Pageable page) {

        var cnpj = filter.getSupplierCnpj() != null ? (filter.getSupplierCnpj() != "" ? filter.getSupplierCnpj().replaceAll("[^0-9]", "") : null) : null;

        if (cnpj != null && !ValidDocuments.getInstance().isCNPJ(cnpj)) {
            throw new IllegalArgumentException("CNPJ anválido: " + cnpj);
        }

        return this.supplierRepository.getAllSuppliers(filter.getSupplierName(), filter.getSupplierCnpj(), page);
    }

    @Override
    public Supplier findById(Long id) {
        return this.supplierRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));
    }

    @Override
    public void updateSupplier(SupplierRequestDto dto, Long id) {

        var supplier = this.findById(id);

        var cnpj = dto.getSupplierCnpj() != null ? (dto.getSupplierCnpj() != "" ? dto.getSupplierCnpj().replaceAll("[^0-9]", "") : null) : null;

        var isValid = ValidDocuments.getInstance().isCNPJ(cnpj);

        if (!isValid) {
            throw new CustomException(ErrorCustom.DOCUMENT_SUPPLIER_INVALID);
        }

        supplier.getAddress().setZipcode(dto.getAddressRequestDto().getZipcode());
        supplier.getAddress().setState(dto.getAddressRequestDto().getState());
        supplier.getAddress().setCity(dto.getAddressRequestDto().getCity());
        supplier.getAddress().setDistrict(dto.getAddressRequestDto().getDistrict());
        supplier.getAddress().setStreet(dto.getAddressRequestDto().getStreet());
        supplier.getAddress().setHomeNumber(dto.getAddressRequestDto().getHomeNumber());
        supplier.setSupplierName(dto.getSupplierName().toUpperCase());
        supplier.setSupplierCnpj(cnpj);
        supplier.setSupplierEmail(dto.getSupplierEmail());
        supplier.setSupplierTelephone(dto.getSupplierTelephone());
        supplier.setUpdateAt(LocalDateTime.now());

        this.supplierRepository.save(supplier);

    }

    @Override
    public void changeStatus(Long id) {

        var supplier = this.findById(id);
        supplier.setActive(false);

        this.supplierRepository.save(supplier);

    }
}
