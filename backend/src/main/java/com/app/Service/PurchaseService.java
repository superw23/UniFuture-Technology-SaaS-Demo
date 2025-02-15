//Made by GRPTeam05
package com.app.Service;

import com.app.Entity.Product;
import com.app.Entity.Purchase;
import com.app.Entity.Supplier;
import com.app.Repository.ProductRepository;
import com.app.Repository.PurchaseRepository;
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
public class PurchaseService {
    @Autowired
    private PurchaseRepository repository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private AveragePriceService averagePriceService;

    //Add a new purchase in the repository.
    public Purchase savePurchase(Purchase purchase){
        Product newProduct = new Product();
        Supplier supplier = supplierRepository.findByCompany(purchase.getSupplierName());
        if(supplier==null)//if the supplier is not found in the supplier list, return null
            return null;
        List<Product> products = productRepository.findAllByName(purchase.getName());
        if(products.size()==0){//if the product is not found, then create a new product in the product list
            newProduct = createNewProduct(purchase);
            purchase.setProductId(newProduct.getId());
        }else {//if the product is found, then check if the supplier can be found
            int hasCorrectSupplier = 0;
            for(Product product : products){
                Supplier productSupplier = supplierRepository.findByCompany(product.getSupplierName());
                if(purchase.getSupplierName().equals(productSupplier.getCompany())) {
                    hasCorrectSupplier = 1;
                    product.setQuantity(product.getQuantity() + purchase.getNum());//update the quantity of this product
                    purchase.setProductId(product.getId());
                    productRepository.save(product);//save the product in the product list
                    break;
                }
            }
            if(hasCorrectSupplier == 0){
                newProduct = createNewProduct(purchase);
                purchase.setProductId(newProduct.getId());
            }
        }
        repository.save(purchase);
        averagePriceService.updateAvgPurchasePrice(purchase);
        return repository.save(purchase);
    }

    //create a new product in the product repository
    public Product createNewProduct(Purchase purchase) {
        Product product = new Product();
        product.setName(purchase.getName());
        product.setType(purchase.getType());
        product.setQuantity(purchase.getNum());
        product.setPurchasePrice(purchase.getPrice());
        product.setSellPrice(0);
        product.setSupplierName(purchase.getSupplierName());
        product.setRemarks(purchase.getRemarks());
        productRepository.save(product);//save the product in the product list
        return product;
    }

    //find all purchases within the start and end date
    public List<Purchase> purchasesWithinGivenDates(String startDate, String endDate){
        List<Purchase> allPurchases = repository.findByDateBetween(startDate,endDate);
        List<Purchase> requiredPurchases = new ArrayList<>();
        for(Purchase purchase : allPurchases){
            if(purchase.getReturnPrice()==0)
                requiredPurchases.add(purchase);
        }
        return requiredPurchases;
    }

    //Find all returned purchases within the start and end date
    public List<Purchase> returnedPurchasesWithinGivenDates(String startDate, String endDate){
        List<Purchase> allPurchases = repository.findByDateBetween(startDate,endDate);
        List<Purchase> requiredPurchases = new ArrayList<>();
        for(Purchase purchase : allPurchases){
            if(purchase.getReturnPrice()!=0)
                requiredPurchases.add(purchase);
        }
        return requiredPurchases;
    }

    //Get all purchase from the repository.
    public List<Purchase> getPurchase(){
        List<Purchase> purchasesWithoutReturn = new ArrayList<>();
        List<Purchase> allPurchases = repository.findAll();
        for(Purchase purchase : allPurchases){
            if(purchase.getNum()!=0)
                purchasesWithoutReturn.add(purchase);
        }
        return purchasesWithoutReturn;
    }

    //Get all return purchase from the repository.
    public List<Purchase> getReturnPurchases(){
        List<Purchase> returnPurchases = new ArrayList<>();
        List<Purchase> allPurchases = repository.findAll();
        for(Purchase purchase : allPurchases){
            if(purchase.getReturnPrice()!=0)
                returnPurchases.add(purchase);
        }
        return returnPurchases;
    }

    //Get the required purchase by its name from the repository.
    public Purchase getPurchaseByName(String name){
        List<Purchase> purchases = repository.findAllByName(name);
        return purchases.get(0);
    }

    //Get the required return purchase by its id from the repository.
    public String returnPurchase(int id){
        Purchase purchase = repository.findById(id).orElse(null);//null if not found
        Product product = productRepository.findById(purchase.getProductId()).orElse(null);
        if(product!=null){
            product.setQuantity(purchase.getNum());
            productRepository.save(product);
            return "Purchase returned ||"+id;
        }
        else
            return "Product not exist";
    }

    //Update the information of the purchase in the repository.
    public Purchase updatePurchase(Purchase purchase, int updateType){
        Purchase existingPurchase = repository.findById(purchase.getId()).orElse(null);
        if(existingPurchase!=null) {
            if(updateType==2) {//return purchase
                purchase.setNum(existingPurchase.getNum()-purchase.getReturnNum());
                purchase.setReturnNum(purchase.getReturnNum() + existingPurchase.getReturnNum());
            }
            else{//update purchase info
                purchase.setNum(purchase.getNum());
                purchase.setReturnNum(purchase.getReturnNum());
            }
            purchase.setTotal(purchase.getPrice()*purchase.getNum());
            purchase.setReturnTotal(purchase.getReturnPrice()*purchase.getReturnNum());
            repository.save(purchase);
            returnPurchase(purchase.getId());
            averagePriceService.updateAvgPurchasePrice(purchase);
            return purchase;
        }
        return null;
    }

    //Get purchases by offset and page size from the repository.
    public List<Purchase> findPurchasesWithPagination(int offset, int pageSize){
        return repository.findByNumGreaterThan(0, PageRequest.of(offset, pageSize));
    }

    //Get return purchases by offset and page size from the repository.
    public List<Purchase> findReturnPurchasesWithPagination(int offset, int pageSize){
        return repository.findByReturnPriceGreaterThan(0, PageRequest.of(offset, pageSize));
    }

    //Get purchases by offset and page size from the repository and sort them by a given field.
    public List<Purchase> findPurchasesWithPaginationAndSorting(int offset,int pageSize,String field){
        return repository.findByNumGreaterThan(0, PageRequest.of(offset, pageSize).withSort(Sort.Direction.ASC,field));
    }

    //Get return purchases by offset and page size from the repository and sort them by a given field.
    public List<Purchase> findReturnPurchasesWithPaginationAndSorting(int offset,int pageSize,String field){
        return repository.findByReturnPriceGreaterThan(0, PageRequest.of(offset, pageSize).withSort(Sort.Direction.ASC,field));
    }

    //Get all types of purchases
    public List<String> purchasesWithDistinctTypes(){
        return productRepository.getDistinctType();
    }
}
