//Made by GRPTeam05
package com.app.Controller;

import com.app.Entity.Supplier;
import com.app.Service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
//supplierController class passes requests from the client and then let the supplierService class deals with them.
public class SupplierController {//we can get or post request via supplier controller
    @Autowired
    private SupplierService service;

    //Add a new supplier
    @PostMapping("/addSupplier")//we can add a supplier in Postman, using json format
    public Supplier addSupplier(@RequestBody Supplier supplier){
        return service.saveSupplier(supplier);
    }

    //Get all supplier records
    @GetMapping("/suppliers")//get a list of suppliers
    public List<Supplier> findAllSuppliers(){
        return service.getSuppliers();
    }

    //Get all suppliers for a given product
    @GetMapping("/suppliersWithGivenProduct/{productName}")
    public List<Supplier> findSuppliersWithGivenProduct(@PathVariable String productName){
        return service.findSuppliersWithGivenProduct(productName);
    }

    //Get the required suppliers by its offset and page size
    @GetMapping("/supplierPagination/{offset}/{pageSize}")
    private List<Supplier> getSuppliersWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<Supplier> suppliersWithPagination = service.findSuppliersWithPagination(offset, pageSize);
        return suppliersWithPagination.getContent();
    }

    //Get the return suppliers record by offset and page size and sort them by a given field
    @GetMapping("/supplierPaginationAndSort/{offset}/{pageSize}/{field}")
    private List<Supplier> getSuppliersWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String field) {
        Page<Supplier> suppliersWithPagination = service.findSuppliersWithPaginationAndSorting(offset, pageSize, field);
        return suppliersWithPagination.getContent();
    }

    //Update the information of the supplier
    @PutMapping("/updateSupplier")
    public Supplier updateSupplier(@RequestBody Supplier supplier){
        return service.updateSupplier(supplier);
    }

    //Delete the supplier by his id.
    @DeleteMapping("/deleteSupplier/{id}")
    public String deleteSupplier(@PathVariable int id){
        return service.deleteSupplier(id);
    }
}
