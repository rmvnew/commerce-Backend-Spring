package com.delta.commerce.service.impl;

import com.delta.commerce.dto.filter.CustomProductFilter;
import com.delta.commerce.dto.request.CustomerProductRequestDto;
import com.delta.commerce.entity.CustomerProduct;
import com.delta.commerce.enums.HistoricDescriptionEnum;
import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.repository.CustomerProductRepository;
import com.delta.commerce.service.CustomerProductService;
import com.delta.commerce.service.HistoricService;
import com.delta.commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerProductServiceImpl implements CustomerProductService {

    @Autowired
    private CustomerProductRepository customerProductRepository;

    @Autowired
    private HistoricService historicService;

    @Autowired
    private UserService userService;

    @Override
    public CustomerProduct createCustomProduct(CustomerProductRequestDto dto) {

        var customProduct = new CustomerProduct();
        customProduct.setCreateAt(LocalDateTime.now());
        customProduct.setUpdateAt(LocalDateTime.now());
        customProduct.setModel(dto.getModel());
        customProduct.setBrand(dto.getBrand());
        customProduct.setActive(true);
        customProduct.setSerialNumber(dto.getSerialNumber());

        var customProductSaved = customerProductRepository.save(customProduct);

        this.historicService.saveHistoric(
                CustomerProduct.class, customProductSaved.getCostumerProductId(),
                this.userService.getLoggedInUser(), HistoricDescriptionEnum.CUSTOMER_PRODUCT_CREATE);

        return customProductSaved;

    }

    @Override
    public CustomerProduct findById(Long id) {
        return this.customerProductRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));
    }

    @Override
    public Page<CustomerProduct> findAllCustomProducts(CustomProductFilter filter, Pageable page) {

        String brand = filter.getBrand() != null ? filter.getBrand() : null;
        String model = filter.getModel() != null ? filter.getModel() : null;
        String serialNumber = filter.getSerialNumber() != null ? filter.getSerialNumber() : null;
        String creatAt = filter.getCreatAt() != null ? filter.getCreatAt() : null;

        return this.customerProductRepository.getAllCustomerProducts(brand, model, serialNumber, creatAt, page);
    }

    @Override
    public CustomerProduct updateCustomProduct(CustomerProductRequestDto dto, Long id) {

        var customProduct = this.findById(id);
        customProduct.setUpdateAt(LocalDateTime.now());
        customProduct.setModel(dto.getModel());
        customProduct.setBrand(dto.getBrand());
        customProduct.setSerialNumber(dto.getSerialNumber());

        var customProductSaved = this.customerProductRepository.save(customProduct);

        this.historicService.saveHistoric(
                CustomerProduct.class, customProductSaved.getCostumerProductId(),
                this.userService.getLoggedInUser(), HistoricDescriptionEnum.CUSTOMER_PRODUCT_UPDATE);

        return customProductSaved;

    }

    @Override
    public void changeStatus(Long id) {

        var customProduct = this.findById(id);
        customProduct.setActive(false);

        var customProductSaved = this.customerProductRepository.save(customProduct);

        this.historicService.saveHistoric(
                CustomerProduct.class, customProductSaved.getCostumerProductId(),
                this.userService.getLoggedInUser(), HistoricDescriptionEnum.CUSTOMER_PRODUCT_DESACTIVED);

    }
}
