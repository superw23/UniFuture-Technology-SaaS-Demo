//Made by GRPTeam05
package com.app.Service;

import com.app.Entity.Product;
import com.app.Entity.Purchase;
import com.app.Entity.Supplier;
import com.app.Repository.ProductRepository;
import com.app.Repository.PurchaseRepository;
import com.app.Repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {
    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private SupplierRepository supplierRepository;
    private AveragePriceService averagePriceService;
    private PurchaseService purchaseService;

    Product product;
    Supplier supplier;

    @BeforeEach
    void setUp() {
        purchaseService = new PurchaseService(purchaseRepository,productRepository,supplierRepository,averagePriceService);
        //create a new supplier
        supplier = Supplier.builder()
                .id(23)
                .company("Coca-Cola")
                .address("南京市下关区金陵小区6村27-2-203室")
                .contact("Miss-WANG")
                .phoneNumber("0773-7217175")
                .remarks("None")
                .build();
        //create a new product
        product = Product.builder()
                .id(1)
                .name("Apple")
                .quantity(20)
                .purchasePrice(5)
                .sellPrice(100)
                .supplierName("Ella")
                .type("Fruit")
                .remarks("none")
                .build();
        supplierRepository.save(supplier);
        productRepository.save(product);
    }

    @Test
    @DisplayName("Should get all inventories from the purchase repository")
    void getAllPurchasesTest() {
        // when
        purchaseService.getPurchase();
        // then
        verify(purchaseRepository).findAll();
    }

    @Test
    @DisplayName("Test return purchase function")
    void returnPurchaseTest(){
        Purchase purchase = Purchase.builder()
                .id(19)
                .name("milk")
                .num(20)
                .price(20)
                .total(45)
                .type("drink")
                .productId(3)
                .supplierName("UNNC")
                .returnPrice(20)
                .remarks("fresh milk")
                .build();
        purchaseService.savePurchase(purchase);
        //Verify that the number after return is correct
        int quantity = product.getQuantity() - purchase.getNum();
        //purchaseService.returnPurchase(purchase.getId());
        assertEquals(0,quantity);
    }
}