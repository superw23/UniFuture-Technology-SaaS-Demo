//Made by GRPTeam05
package com.app.Repository;

import com.app.Entity.Purchase;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PurchaseRepositoryTest {
    Purchase purchase;

    @Mock
    private PurchaseRepository purchaseRepository = mock(PurchaseRepository.class);

    @BeforeEach
    public void setUp(){
        purchase = Purchase.builder()
                .id(19)
                .name("milk")
                .num(30)
                .price(20)
                .total(45)
                .type("drink")
                .productId(1)
                .supplierName("UNNC")
                .remarks("fresh milk")
                .build();
    }

    //JUnit5 test for saveCustomer
    @Test
    public void savePurchaseTest(){
        purchaseRepository.save(purchase);
        verify(purchaseRepository).save(purchase);
    }

    @Test
    public void getAllPurchasesTest(){
        purchaseRepository.findAll();
        verify(purchaseRepository).findAll();
    }

    @Test
    public void findAllByNameTest(){
        purchaseRepository.findAllByName("Sally");
        verify(purchaseRepository).findAllByName("Sally");
    }

    @Test
    public void findByReturnPriceTest(){
        int offset = 5;
        int pageSize = 10;
        purchaseRepository.findByNumGreaterThan(1, PageRequest.of(offset, pageSize));
        verify(purchaseRepository).findByNumGreaterThan(1, PageRequest.of(offset, pageSize));
    }

    @Test
    public void findAllByProductIdTest(){
        purchaseRepository.findAllByProductId(1);
        verify(purchaseRepository).findAllByProductId(1);
    }

    @Test
    public void findByNameContainingTest(){
        purchaseRepository.findByNameContaining("a");
        verify(purchaseRepository).findByNameContaining("a");
    }

    @Test
    public void findByReturnPriceGreaterThanTest(){
        int offset = 5;
        int pageSize = 10;
        purchaseRepository.findByReturnPriceGreaterThan(1, PageRequest.of(offset, pageSize));
        verify(purchaseRepository).findByReturnPriceGreaterThan(1, PageRequest.of(offset, pageSize));
    }
}