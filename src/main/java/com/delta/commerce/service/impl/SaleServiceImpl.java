package com.delta.commerce.service.impl;

import com.delta.commerce.dto.request.SaleRequestDto;
import com.delta.commerce.entity.Product;
import com.delta.commerce.entity.Sale;
import com.delta.commerce.entity.SaleProduct;
import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.repository.*;
import com.delta.commerce.service.SaleService;
import com.delta.commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleProductRepository saleProductRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public void createSale(SaleRequestDto dto) {

        var invoice = this.invoiceRepository.findById(dto.getInvoice_id());
        var client = this.clientRepository.findById(dto.getClient_id());
        BigDecimal totalValue = BigDecimal.ZERO;

        var sale = new Sale();
        sale.setSaleCode(UUID.randomUUID().toString());
        sale.setCreateAt(LocalDateTime.now());
        sale.setSaleProducts(null);
        sale.setInvoice(invoice.orElse(null));
        sale.setUser(userService.getLoggedInUser());
        sale.setClient(client.orElse(null));
        sale.setTotalValue(totalValue);

        var saleSaved = this.saleRepository.save(sale);

        var products = dto.getProducts();
        List<SaleProduct> saleProducts = new ArrayList<>();
        List<Product> updatedProducts = new ArrayList<>();

        for (Map.Entry<Long, Double> entry : products.entrySet()) {

            var product = this.productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));

            double quantity = entry.getValue();

            if (product.getProductQuantity() < quantity) {
                throw new CustomException(ErrorCustom.NOT_ENOUGH_QUANTITY_OF_PRODUCT);
            }

            var saleProduct = new SaleProduct(saleSaved, product, quantity);
            saleProducts.add(saleProduct);

            BigDecimal productValue = product.getProductUnitPrice().multiply(new BigDecimal(quantity));
            totalValue = totalValue.add(productValue);

            product.setProductQuantity(product.getProductQuantity() - quantity);
            updatedProducts.add(product);
        }

        this.saleProductRepository.saveAll(saleProducts);
        this.productRepository.saveAll(updatedProducts);

        saleSaved.setTotalValue(totalValue);
        this.saleRepository.save(saleSaved);
    }

    @Override
    public Sale getSaleByCode(String code) {
        return this.saleRepository.getSaleByCode(code);
    }
}
