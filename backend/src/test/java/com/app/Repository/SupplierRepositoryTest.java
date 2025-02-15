//Made by GRPTeam05
package com.app.Repository;

import com.app.Entity.Supplier;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SupplierRepositoryTest {
    Supplier supplier;

    @Mock
    private SupplierRepository supplierRepository;

    //JUnit5 test for saveSupplier
    @Test
    public void saveSupplierTest(){
        supplierRepository.save(supplier);
        verify(supplierRepository).save(supplier);
    }

    @Test
    public void getAllSuppliersTest(){
        supplierRepository.findAll();
        verify(supplierRepository).findAll();
    }

    @Test
    public void saveSupplierNameTest(){
        supplier = Supplier.builder()
                .id(1)
                .company("Coca-Cola")
                .address("UNNC")
                .contact("Miss-WANG")
                .phoneNumber("0773-7217175")
                .remarks("None")
                .build();
    }

    @Test
    public void findByCompanyTest() {
        supplierRepository.save(supplier);
        verify(supplierRepository).save(supplier);
    }

    @Test
    public void findByCompanyContainingTest(){
        supplierRepository.findByCompanyContaining("c");
        verify(supplierRepository).findByCompanyContaining("c");
    }
}