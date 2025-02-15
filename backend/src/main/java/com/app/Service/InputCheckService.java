//Made by GRPTeam05
package com.app.Service;

import com.app.Entity.Customer;
import com.app.Entity.Product;
import com.app.Entity.Supplier;
import com.app.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class InputCheckService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    SaleRepository saleRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    CustomerRepository customerRepository;

    public boolean checkSupplierExistence(String supplierName){
        Supplier supplier = supplierRepository.findByCompany(supplierName);
        if(supplier!=null)
            return true;
        return false;
    }

    public boolean checkCustomerExistence(String customerName){
        Customer customer = customerRepository.findByName(customerName);
        if(customer!=null)
            return true;
        return false;
    }

    public boolean checkProductExistence(String productName, String supplierName){
        Product product = productRepository.findByNameAndSupplierName(productName, supplierName);
        if(product!=null)
            return true;
        return false;
    }

    public boolean checkAvailability(String productName, String supplierName, int saleNum){
        Product product = productRepository.findByNameAndSupplierName(productName, supplierName);
        int availability = product.getQuantity();
        if(availability < saleNum)
            return false;
        else
            return true;
    }
}
