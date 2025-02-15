//Made by GRPTeam05
package com.app.Service;

import com.app.Entity.Customer;
import com.app.Entity.Product;
import com.app.Repository.CustomerRepository;
import com.app.Repository.ProductRepository;
import com.app.Repository.SaleRepository;
import com.app.Repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {
    @Mock
    private SaleRepository saleRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private SupplierRepository supplierRepository;
    private AveragePriceService averagePriceService;
    private SaleService saleService;
    private ProductService productService;
    private CustomerService customerService;

    Product product;
    Customer customer;

    @BeforeEach
    void setup(){
        customerService = new CustomerService(customerRepository);
        saleService = new SaleService(saleRepository,productRepository,supplierRepository,averagePriceService);
        product = Product.builder()
                .id(2)
                .name("Apple")
                .quantity(20)
                .purchasePrice(5)
                .sellPrice(100)
                .type("Fruit")
                .remarks("none")
                .build();
        productRepository.save(product);
        customer = Customer.builder()
                .id(10)
                .name("Sally")
                .address("UNNC")
                .contact("Sam")
                .phoneNumber("666")
                .remarks("none")
                .build();
        customerRepository.save(customer);
    }

    @Test
    @DisplayName("Get the sale record from its repository")
    void getSaleTest(){
        //when
        saleService.getSales();
        //then
        verify(saleRepository,atLeast(1)).findAll();
    }
}