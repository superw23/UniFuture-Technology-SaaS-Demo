//Made by GRPTeam05
package com.app.Controller;

import com.app.Entity.Customer;
import com.app.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
//CustomerController class passes requests from the client and then let the CustomerService class deals with them.
public class CustomerController {
    @Autowired
    private CustomerService service;

    //Add a new customer.
    @PostMapping("/addCustomer")
    public Customer addCustomer(@RequestBody Customer customer){
        return service.saveCustomer(customer);
    }

    //Get all customers.
    @GetMapping("/customers")
    public List<Customer> findAllCustomers(){
        return service.getCustomers();
    }

    //Get customers by offset and page size.
    @GetMapping("/customerPagination/{offset}/{pageSize}")
    private List<Customer> getCustomersWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<Customer> customersWithPagination = service.findCustomersWithPagination(offset, pageSize);
        return customersWithPagination.getContent();
    }

    //Get customers by offset and page size and sort them by a given field.
    @GetMapping("/customerPaginationAndSort/{offset}/{pageSize}/{field}")
    private List<Customer> getCustomersWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String field) {
        Page<Customer> customersWithPagination = service.findCustomersWithPaginationAndSorting(offset, pageSize, field);
        return customersWithPagination.getContent();
    }

    //Update the information of the customer.
    @PutMapping("/updateCustomer")
    public Customer updateCustomer(@RequestBody Customer customer){
        return service.updateCustomer(customer);
    }

    //Delete the customer by his id.
    @DeleteMapping("/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable int id){
        return service.deleteCustomer(id);
    }
}
