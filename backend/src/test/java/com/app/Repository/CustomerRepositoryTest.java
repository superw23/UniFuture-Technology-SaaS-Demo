//Made by GRPTeam05
package com.app.Repository;

import com.app.Entity.Customer;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {
    Customer customer;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customer = Customer.builder()
                .id(1)
                .name("Sally")
                .address("UNNC")
                .contact("Sam")
                .phoneNumber("666")
                .remarks("none")
                .build();
    }

    //JUnit5 test for saveCustomer
    @Test
    public void saveCustomerTest(){
        customerRepository.save(customer);
        verify(customerRepository).save(customer);
    }

    @Test
    public void getCustomersTest(){
        customerRepository.findAll();
        verify(customerRepository).findAll();
    }

}