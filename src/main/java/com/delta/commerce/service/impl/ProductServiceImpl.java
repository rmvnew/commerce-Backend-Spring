package com.delta.commerce.service.impl;

import com.delta.commerce.dto.filter.ProductFilter;
import com.delta.commerce.dto.request.AddProductsRequestDto;
import com.delta.commerce.dto.request.ProductRequestDto;
import com.delta.commerce.dto.response.ProductSaleResponseDto;
import com.delta.commerce.entity.Category;
import com.delta.commerce.entity.Invoice;
import com.delta.commerce.entity.InvoiceLine;
import com.delta.commerce.entity.Product;
import com.delta.commerce.enums.HistoricDescriptionEnum;
import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.mappers.ProductMapper;
import com.delta.commerce.repository.CategoryRepository;
import com.delta.commerce.repository.InvoiceLineRepository;
import com.delta.commerce.repository.ProductRepository;
import com.delta.commerce.service.*;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceLineRepository invoiceLineRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoricService historicService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Product createProduct(ProductRequestDto dto) {

        var isRegistered = this.productRepository
                .findByProductNameAndBarcode(dto.getProductName().toUpperCase(), dto.getProductBarcode());

        if (isRegistered.isPresent()) {
            throw new CustomException(ErrorCustom.PRODUCT_ALREADY_EXISTS);
        }

        var category = this.getCategoryById(dto.getCategoryId());

        var product = new Product();
        product.setCreateAt(LocalDateTime.now());
        product.setUpdateAt(LocalDateTime.now());
        product.setActive(true);
        product.setProductName(dto.getProductName().toUpperCase());
        product.setProductBarcode(dto.getProductBarcode());
        product.setProductCode(dto.getProductCode());
        product.setProductLocation(dto.getProductLocation());
        product.setProductNcm(dto.getProductNcm());
        product.setProductCfop(dto.getProductCfop());
        product.setProductUnitOfMeasurement(dto.getProductUnitOfMeasurement());
        product.setProductQuantity(dto.getProductQuantity());
        product.setProductMinimumStock(dto.getProductMinimumStock());
        product.setProductUnitCost(dto.getProductUnitCost());
        product.setProductUnitPrice(dto.getProductUnitPrice());
        product.setCategory(category);

        var productSaved = this.productRepository.save(product);

        if (dto.getInvoiceNumber() != null && !dto.getInvoiceNumber().equals("")) {
            var invoice = this.invoiceService.findByNumber(dto.getInvoiceNumber());
            var invoiceLine = new InvoiceLine();
            invoiceLine.setInvoice(invoice);
            invoiceLine.setProduct(productSaved);
            invoiceLine.setQuantity(dto.getProductQuantity());

            this.invoiceLineRepository.save(invoiceLine);
        }

        this.historicService.saveHistoric(
                Product.class, productSaved.getProductId(),
                this.userService.getLoggedInUser(), HistoricDescriptionEnum.PRODUCT_CREATE);

        return productSaved;
    }

    @Override
    public Product findById(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));
    }

    @Override
    public Page<ProductSaleResponseDto> getAllProducts(ProductFilter filter, Pageable page) {


        Page<Product> products = this.productRepository.getAllProduct(
                filter.getProductName(),
                filter.getProductBarcode() != null ? (filter.getProductBarcode() != "" ? filter.getProductBarcode() : null) : null,
                filter.getProductCode() != null ? (filter.getProductCode() != "" ? filter.getProductCode() : null) : null,
                filter.getProductNcm() != null ? (filter.getProductNcm() != "" ? filter.getProductNcm() : null) : null,
                page
        );

        return products.map(productMapper::toDto);
    }

    @Override
    public Product updateProduct(ProductRequestDto dto, Long id) {

        var product = this.findById(id);

        product.setUpdateAt(LocalDateTime.now());
        product.setProductName(dto.getProductName() != null ? dto.getProductName().toUpperCase() : product.getProductName());
        product.setProductBarcode(dto.getProductBarcode() != null ? dto.getProductBarcode() : product.getProductBarcode());
        product.setProductCode(dto.getProductCode() != null ? dto.getProductCode() : product.getProductCode());
        product.setProductLocation(dto.getProductLocation() != null ? dto.getProductLocation() : product.getProductLocation());
        product.setProductNcm(dto.getProductNcm() != null ? dto.getProductNcm() : product.getProductNcm());
        product.setProductCfop(dto.getProductCfop() != null ? dto.getProductCfop() : product.getProductCfop());
        product.setProductUnitOfMeasurement(dto.getProductUnitOfMeasurement() != null ? dto.getProductUnitOfMeasurement() : product.getProductUnitOfMeasurement());
        product.setProductQuantity(dto.getProductQuantity() != null ? dto.getProductQuantity() : product.getProductQuantity());
        product.setProductMinimumStock(dto.getProductMinimumStock() != null ? dto.getProductMinimumStock() : product.getProductMinimumStock());
        product.setProductUnitCost(dto.getProductUnitCost() != null ? dto.getProductUnitCost() : product.getProductUnitCost());
        product.setProductUnitPrice(dto.getProductUnitPrice() != null ? dto.getProductUnitPrice() : product.getProductUnitPrice());
        product.setCategory(dto.getCategoryId() != null ? this.getCategoryById(dto.getCategoryId()) : product.getCategory());

        var productSaved = this.productRepository.save(product);

        if (dto.getInvoiceNumber() != null && !dto.getInvoiceNumber().equals("")) {

            Invoice invoice = this.invoiceService.findByNumber(dto.getInvoiceNumber());

            if (!(invoice.getInvoiceLines().size() > 0)) {

                var invoiceLine = new InvoiceLine();
                saveInvoiceLine(invoiceLine, invoice, productSaved, dto.getProductQuantity());
            } else {
                invoice.getInvoiceLines().forEach(data -> {
                    saveInvoiceLine(data, invoice, productSaved, dto.getProductQuantity());
                });

            }

        }

        this.historicService.saveHistoric(
                Product.class, productSaved.getProductId(),
                this.userService.getLoggedInUser(), HistoricDescriptionEnum.PRODUCT_CREATE);

        return productSaved;


    }

    private void saveInvoiceLine(InvoiceLine invoiceLine, Invoice invoice, Product productSaved, Double dto) {
        invoiceLine.setInvoice(invoice);
        invoiceLine.setProduct(productSaved);
        invoiceLine.setQuantity(dto);

        this.invoiceLineRepository.save(invoiceLine);
    }

    @Override
    public Category getCategoryById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCustom.CATEGORY_NOT_FOUND));
    }

    @Override
    public Product addProductsToStock(AddProductsRequestDto dto, String barcode) {

        var product = this.productRepository.findByProductBarcode(barcode)
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));

        Double currentQuantity = dto.getQuantity() + product.getProductQuantity();

        product.setProductQuantity(currentQuantity);

        var productSaved = this.productRepository.save(product);

        if (!dto.getInvoiceNumber().equals("")) {
            var invoice = this.invoiceService.findByNumber(dto.getInvoiceNumber());
            var invoiceLine = new InvoiceLine();
            invoiceLine.setInvoice(invoice);
            invoiceLine.setProduct(productSaved);
            invoiceLine.setQuantity(dto.getQuantity());

            this.invoiceLineRepository.save(invoiceLine);
        }

        return productSaved;
    }

    @Override
    public void changeStatus(Long id) {

        var product = this.findById(id);
        product.setActive(false);
        var productSaved = this.productRepository.save(product);

        this.historicService.saveHistoric(
                Product.class, productSaved.getProductId(),
                this.userService.getLoggedInUser(), HistoricDescriptionEnum.PRODUCT_CREATE);

    }

    @Override
    public void importFromCSVFile(MultipartFile file) {
        List<ProductRequestDto> products = new ArrayList<>();
        List<Product> createdProducts = new ArrayList<>();
        try (Reader reader = new InputStreamReader(file.getInputStream())) {

            CSVReader csvReader = new CSVReader(reader);
            String[] nextRecord;

            // Pula o cabeçalho
            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {

                var category = this.categoryService.findByCategoryName(nextRecord[11]);

                var prod = new ProductRequestDto();

                prod.setProductName(nextRecord[0]);
                prod.setProductBarcode(nextRecord[1]);
                prod.setProductCode(nextRecord[2]);
                prod.setProductLocation(nextRecord[3]);
                prod.setProductNcm(nextRecord[4]);
                prod.setProductCfop(nextRecord[5]);
                prod.setProductUnitOfMeasurement(nextRecord[6]);
                prod.setProductQuantity(Double.parseDouble(nextRecord[7]));
                prod.setProductMinimumStock(Double.parseDouble(nextRecord[8]));
                prod.setProductUnitCost(BigDecimal.valueOf(Double.parseDouble(nextRecord[9])));
                prod.setProductUnitPrice(BigDecimal.valueOf(Double.parseDouble(nextRecord[10])));
                prod.setCategoryId(category.getCategoryId());



                products.add(prod);
            }


            for(ProductRequestDto pro: products){

               createdProducts.add(this.createProduct(pro));

            }

        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }



    }


}
