//Made by GRPTeam05
package com.app.Service;

import com.app.Entity.Product;
import com.app.Entity.Supplier;
import com.app.Repository.ProductRepository;
import com.app.Repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
//ProductService class interacts with the ProductRepository class to perform actions asked by the ProductController class.
public class ProductService {
    @Autowired
    private ProductRepository repository;//it is the product service that connects with the product repository, not product controller
    @Autowired
    private SupplierRepository supplierRepository;

    //Add a new product in the repository.
    public Product saveProduct(Product product){//save the product in database
        Supplier supplier = supplierRepository.findByCompany(product.getSupplierName());
        if(supplier==null)//supplier does not exist
            return null;
        Product p = repository.findByNameAndSupplierName(product.getName(),product.getSupplierName());
        if (p!=null) {
            p.setQuantity(product.getQuantity() + p.getQuantity());
            repository.save(p);
        }
        return repository.save(product);//save the new product in the product repository if it does not exist
    }

    //Get all products from the product repository and group products with the same name
    public List<Product> sortProductsByName(){
        List<Product> products = findProductsWithASCSorting("name");
        List<Product> productsGroupByName = new ArrayList<>();
        while(!products.isEmpty()){
            int total = 0;
            Product p = products.get(0);
            String productName = p.getName();
            List<Product> productsWithSameName = repository.findGroupByName(productName);
            for(Product product : productsWithSameName){
                total += product.getQuantity();
            }
            p.setSellPrice(getAverageSellPrice(p.getName()));
            p.setPurchasePrice(getAveragePurchasePrice(p.getName()));
            p.setQuantity(total);
            if(total!=0)
                productsGroupByName.add(p);
            products.removeAll(productsWithSameName);
        }
        return productsGroupByName;
    }

    //Get all products from the product repository
    public List<Product> getAllProducts(){
        return repository.findAll();
    }

    //Get average sale price of products with the same name
    public double getAverageSellPrice(String productName){
        List<Product> productsWithSameName = repository.findGroupByName(productName);
        double totalSellPrice = 0;
        double averageSellPrice = 0;
        int totalQuantity = 0;
        for(Product product : productsWithSameName){
            totalSellPrice += product.getSellPrice()*product.getQuantity();
            totalQuantity += product.getQuantity();
        }
        averageSellPrice = totalSellPrice / totalQuantity;
        return averageSellPrice;
    }

    //Get average purchase price of products with the same name
    public double getAveragePurchasePrice(String productName){
        List<Product> productsWithSameName = repository.findGroupByName(productName);
        double totalPurchasePrice = 0;
        double averagePurchasePrice = 0;
        int totalQuantity = 0;
        for(Product product : productsWithSameName){
            totalPurchasePrice += product.getPurchasePrice()*product.getQuantity();
            totalQuantity += product.getQuantity();
        }
        averagePurchasePrice = totalPurchasePrice / totalQuantity;
        return averagePurchasePrice;
    }

    //Get the remaining quantity of a given product from the product repository
    public int getRemainingQuantity(String name, String supplier){
        Product product = repository.findByNameAndSupplierName(name, supplier);
        if(product!=null)
            return product.getQuantity();
        else
            return -1;
    }

    //Get products by a given field in ascending order from the repository.
    public List<Product> findProductsWithASCSorting(String field){
        return repository.findAll(Sort.by(Sort.Direction.ASC,field));
    }

    //Get products by offset and page size from the repository.
    public List<Product> findProductsWithPagination(int offset,int pageSize){
        return repository.getProductsByDistinctName(PageRequest.of(offset, pageSize));
    }

    //Get products by offset and page size from the repository and sort them by a given field.
    public List<Product> findProductsWithPaginationAndSorting(int offset,int pageSize,String field){
        return repository.getProductsByDistinctName(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
    }

    //Get the required products by type from the repository.
    public List<Product> findProductsWithGivenType(String typeName){
        return repository.findAllByType(typeName);
    }

    //Delete the product by its name from the repository.
    public String deleteProductByName(String name){//delete a product with a given name in database
        repository.deleteAllByName(name);
        return "Product removed || "+name;
    }
}
