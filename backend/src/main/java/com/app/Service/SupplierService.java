//Made by GRPTeam05
package com.app.Service;

import com.app.Entity.Product;
import com.app.Entity.Supplier;
import com.app.Repository.ProductRepository;
import com.app.Repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
//supplierService class interacts with the supplierRepository class to perform actions asked by the supplierController class.
public class SupplierService {
    @Autowired
    private SupplierRepository repository;
    @Autowired
    private ProductRepository productRepository;

    //Add a new supplier in the repository
    public Supplier saveSupplier(Supplier supplier){
        Supplier s = repository.findByCompany(supplier.getCompany());
        if(s != null)//supplier with the same name has already existed in the repository
            return null;
        return repository.save(supplier);
    }

    //Get all suppliers from the repository
    public List<Supplier> getSuppliers(){
        return repository.findAll();
    }

    public List<Supplier> findSuppliersWithGivenProduct(String productName){
        List<Product> products = productRepository.findAllByName(productName);
        List<Supplier> suppliers = new ArrayList<>();
        for(Product product : products){
            Supplier supplier = repository.findByCompany(product.getSupplierName());
            if(supplier!=null)
                suppliers.add(supplier);
        }
        return suppliers;
    }

    //Get suppliers by offset and page size from the repository.
    public Page<Supplier> findSuppliersWithPagination(int offset, int pageSize){
        Page<Supplier> supplier = repository.findAll(PageRequest.of(offset, pageSize));
        return  supplier;
    }

    //Get suppliers by offset and page size from the repository and sort them by a given field.
    public Page<Supplier> findSuppliersWithPaginationAndSorting(int offset,int pageSize,String field){
        Page<Supplier> suppliers = repository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return  suppliers;
    }

    //update supplier information
    public Supplier updateSupplier(Supplier supplier){
        Supplier existingSupplier = repository.findById(supplier.getId()).orElse(null);
        existingSupplier.setCompany(supplier.getCompany());
        existingSupplier.setAddress(supplier.getAddress());
        existingSupplier.setContact(supplier.getContact());
        existingSupplier.setPhoneNumber(supplier.getPhoneNumber());
        existingSupplier.setRemarks(supplier.getRemarks());
        return repository.save(existingSupplier);
    }

    //Delete supplier by its id
    public String deleteSupplier(int id){
        repository.deleteById(id);
        return "Supplier removed || " + id;
    }
}
