//Made by GRPTeam05
package com.app.Service;

import com.app.Entity.Customer;
import com.app.Repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
//CustomerService class interacts with the CustomerRepository class to perform actions asked by the CustomerController class.
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    //Add a new customer in the repository.
    public Customer saveCustomer(Customer customer){
        Customer c = repository.findByName(customer.getName());
        if(c!=null)//customer with the same name has already existed in the repository
            return null;
        return repository.save(customer);
    }

    //Get all customers from the repository.
    public List<Customer> getCustomers(){
        return repository.findAll();
    }

    //Delete the customer by his id from the repository.
    public String deleteCustomer(int id){
        repository.deleteById(id);
        return "Customer removed || "+id;
    }

    //Update the information of the customer in the repository.
    public Customer updateCustomer(Customer customer){
        Customer existingCustomer = repository.findById(customer.getId()).orElse(null);
        existingCustomer.setName(customer.getName());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setContact(customer.getContact());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        existingCustomer.setRemarks(customer.getRemarks());
        return repository.save(existingCustomer);
    }

    //Get customers by offset and page size from the repository.
    public Page<Customer> findCustomersWithPagination(int offset, int pageSize){
        Page<Customer> customers = repository.findAll(PageRequest.of(offset, pageSize));
        return  customers;
    }

    //Get customers by offset and page size from the repository and sort them by a given field.
    public Page<Customer> findCustomersWithPaginationAndSorting(int offset,int pageSize,String field){
        Page<Customer> customers = repository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return  customers;
    }
}
