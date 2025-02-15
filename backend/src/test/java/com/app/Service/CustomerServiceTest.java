//Made by GRPTeam05
package com.app.Service;

import com.app.Entity.Customer;
import com.app.Repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository);
    }

    @Test
    @DisplayName("Should get all customers from customer repository")
    void getAllCustomersTest() {
        // when
        customerService.getCustomers();
        // then
        verify(customerRepository).findAll();
    }

    @Test
    @DisplayName("Should add a new customer to the customer repository")
    void saveCustomerTest() {
        //create a new customer
        Customer customer = Customer.builder()
                .id(1)
                .name("Sally")
                .address("UNNC")
                .contact("Sam")
                .phoneNumber("666")
                .remarks("none")
                .build();

        //add the customer in the repository
        customerService.saveCustomer(customer);

        //create a customerArgumentCaptor
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        //check if the customerRepository saves the customerArgumentCaptor
        verify(customerRepository)
                .save(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        //check if the customerRepository saves the same customer as the new customer created above
        assertThat(capturedCustomer).isEqualTo(customer);
    }

    @Test
    @DisplayName("Should delete a customer by id from the customer repository")
    void deleteCustomerTest() {
        int id = 10;
        customerService.deleteCustomer(id);
        verify(customerRepository).deleteById(id);
    }
}