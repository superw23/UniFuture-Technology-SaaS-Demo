//Made by GRPTeam05
package com.app.Service;

import com.app.Entity.Supplier;
import com.app.Repository.ProductRepository;
import com.app.Repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private ProductRepository productRepository;
    private SupplierService supplierService;

    @BeforeEach
    void setUp() {
        supplierService = new SupplierService(supplierRepository, productRepository);
    }

    @Test
    @DisplayName("Return all suppliers from supplier repository")
    void getSupplierTest(){
        //when
        supplierService.getSuppliers();
        //then
        verify(supplierRepository,atLeast(1)).findAll();    // Access the repository at least once
    }

    @Test
    @DisplayName("Should add a new supplier to the supplier repository")
    void saveSupplierTest(){
        //create a new supplier
        Supplier supplier = Supplier.builder()
                .id(1)
                .company("Coca-Cola")
                .address("南京市下关区金陵小区6村27-2-203室")
                .contact("Miss-WANG")
                .phoneNumber("0773-7217175")
                .remarks("None")
                .build();
        //add supplier to the repository
        supplierService.saveSupplier(supplier);
        //create a supplierArgumentCaptor
        ArgumentCaptor<Supplier> supplierArgumentCaptor = ArgumentCaptor.forClass(Supplier.class);

        //check if the supplierRepository saves the supplierArgumentCaptor
        verify(supplierRepository,atLeast(1)).save(supplierArgumentCaptor.capture());
        Supplier capturedSupplier = supplierArgumentCaptor.getValue();

        //check if the supplierRepository save the same supplier as the new created supplier
        assertEquals(capturedSupplier, supplier);
    }
    @Test
    @DisplayName("Should delete a supplier by id from its repository")
    void deleteSupplierTest(){
        int id = 1;
        supplierService.deleteSupplier(id);
        verify(supplierRepository,atLeast(1)).deleteById(id);
    }
}