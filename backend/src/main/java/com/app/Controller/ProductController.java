//Made by GRPTeam05
package com.app.Controller;

import com.app.Entity.Product;
import com.app.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
//ProductController class passes requests from the client and then let the ProductService class deals with them.
public class ProductController {
    @Autowired
    private ProductService service;//use product service to connect with repository, then reaching database

    //Add a new product
    @PostMapping("/addProduct")//we can add a product in Postman, using json format
    public Product addProduct(@RequestBody Product product){
        return service.saveProduct(product);
    }

    //Get all products and group products with the same name
    @GetMapping("/sortProductsByName")
    public List<Product> sortProductsByName(){
        return service.sortProductsByName();
    }

    //Get all products from the inventory
    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return service.getAllProducts();
    }

    //Get the required products by its offset and page size
    @GetMapping("/remainingQuantity/{name}/{supplier}")
    public int getProductsWithPagination(@PathVariable String name, @PathVariable String supplier) {
        return service.getRemainingQuantity(name, supplier);
    }

    //Get all records in a certain page
    @GetMapping("/productPagination/{offset}/{pageSize}")
    public List<Product> getProductsWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        return service.findProductsWithPagination(offset, pageSize);
    }

    //Get the return products record by offset and page size and sort them by a given field
    @GetMapping("/productPaginationAndSort/{offset}/{pageSize}/{field}")
    public List<Product> getProductsWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String field) {
        return service.findProductsWithPaginationAndSorting(offset, pageSize, field);
    }

    //Get all products of a given type
    @GetMapping("/productsWithGivenType/{typeName}")
    public List<Product> findProductsWithGivenType(@PathVariable String typeName){
        return service.findProductsWithGivenType(typeName);
    }

    //Delete the product by his name.
    @DeleteMapping("/deleteProductByName/{name}")//delete a product with a given id
    public String deleteProductByName(@PathVariable String name){
        return service.deleteProductByName(name);
    }
}
