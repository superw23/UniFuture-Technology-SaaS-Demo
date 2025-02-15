//Made by GRPTeam05
package com.app.Repository;

import com.app.Entity.Sale;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SaleRepositoryTest {
    Sale sale;

    @Mock
    private SaleRepository saleRepository;

    @BeforeEach
    public void setUp(){
        sale = Sale.builder()
                .id(1)
                .date("2023-03-15")
                .num(10)
                .price(20)
                .total(200)
                .productId(1)
                .customerName("Tom")
                .build();
    }

    //JUnit5 test for saveSale
    @Test
    public void saveSaleTest(){
        saleRepository.save(sale);
        verify(saleRepository).save(sale);
    }

    @Test
    public void getAllSalesTest(){
        saleRepository.findAll();
        verify(saleRepository).findAll();
    }
    @Test
    public void findByDateTest() {
        saleRepository.findByDateBetween("2022-12-23","2023-01-30");
        verify(saleRepository).findByDateBetween("2022-12-23","2023-01-30");
    }

    @Test
    public void findAllByDateTest() {
        saleRepository.findAllByDate("2023-03-15");
        verify(saleRepository).findAllByDate("2023-03-15");
    }

    @Test
    public void getProductSaleNumTest() {
        saleRepository.getProductSaleNum("Chips");
        verify(saleRepository).getProductSaleNum("Chips");
    }

    @Test
    void getDistinctTypeTest() {
        saleRepository.getDistinctType();
        verify(saleRepository).getDistinctType();
    }

    @Test
    void getTotalNumByTypeTest() {
        saleRepository.getTotalNumByType("Food");
        verify(saleRepository).getTotalNumByType("Food");
    }
    @Test
    void findAllByNameTest() {
        saleRepository.findAllByName("Tom");
        verify(saleRepository).findAllByName("Tom");
    }

    @Test
    void findByNumGreaterThanTest() {
        int offset = 0;
        int pageSize = 5;
        String field = "Food";
        saleRepository.findByNumGreaterThan(0, PageRequest.of(offset, pageSize).withSort(Sort.Direction.ASC,field));
        verify(saleRepository).findByNumGreaterThan(0, PageRequest.of(offset, pageSize).withSort(Sort.Direction.ASC,field));
    }
}