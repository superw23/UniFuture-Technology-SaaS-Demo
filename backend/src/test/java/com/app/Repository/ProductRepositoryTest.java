//Made by GRPTeam05
package com.app.Repository;

import com.app.Entity.Product;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRepositoryTest {
    Product product;

    @Mock
    ProductRepository productRepository = Mockito.mock(ProductRepository.class);

    @BeforeEach
    public void setUp(){
        product = Product.builder()
                .id(1)
                .name("Milk")
                .quantity(100)
                .purchasePrice(12)
                .sellPrice(66)
                .type("Drink")
                .remarks("none")
                .build();
    }

    //JUnit5 test for saveInventory
    @Test
    public void saveProductTest(){
        productRepository.save(product);
        verify(productRepository).save(product);
    }

    @Test
    public void getProductsTest(){
        productRepository.findAll();
        verify(productRepository).findAll();
    }

    @Test
    public void findGroupByNameTest(){
        productRepository.findGroupByName("Chips");
        verify(productRepository).findGroupByName("Chips");
    }

    @Test
    public void getDistinctTypeTest(){
        productRepository.getDistinctType();
        verify(productRepository).getDistinctType();
    }
}