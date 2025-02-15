//Made by GRPTeam05
package com.app.Service;

import com.app.Entity.Product;
import com.app.Entity.Sale;
import com.app.Entity.Supplier;
import com.app.Repository.ProductRepository;
import com.app.Repository.SaleRepository;
import com.app.Repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
//saleService class interacts with the saleRepository class to perform actions asked by the saleController class.
public class SaleService {
    @Autowired
    private SaleRepository repository;//it is sale service that connects with repository, not sale controller
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private AveragePriceService averagePriceService;

    //Add a new sale in the repository.
    public Sale saveSale(Sale sale){
        Supplier supplier = supplierRepository.findByCompany(sale.getSupplierName());
        Product product = productRepository.findByNameAndSupplierName(sale.getName(), supplier.getCompany());//null if the sale record is not found
        if(product==null)//if the product is not found, return null
            return null;
        sale.setProductId(product.getId());
        sale.setType(product.getType());
        product.setQuantity(product.getQuantity() - sale.getNum());//update the quantity of this product
        product.setSellPrice(sale.getPrice());
        productRepository.save(product);
        repository.save(sale);
        averagePriceService.updateAvgSalePrice(sale);
        return repository.save(sale);
    }

    //Get all sales records.
    public List<Sale> getSales(){
        List<Sale> salesWithoutReturn = new ArrayList<>();
        List<Sale> allSales = repository.findAll();
        for(Sale sale : allSales){
            if(sale.getNum()!=0)
                salesWithoutReturn.add(sale);
        }
        return salesWithoutReturn;
    }

    //Get all return sales from the repository.
    public List<Sale> getReturnSales(){
        List<Sale> returnSales = new ArrayList<>();
        List<Sale> allSales = repository.findAll();
        for(Sale sale : allSales){
            if(sale.getReturnPrice()!=0)
                returnSales.add(sale);
        }
        return returnSales;
    }

    //Get total revenue of the most popular type
    public double getTotalOfMostPopularTypeRevenue(){
        double totalOfMostPopularTypeRevenue = 0;
        String mostPopularType = getMostPopularType();
        List<Sale> allSales = repository.findAll();
        for(Sale sale : allSales){
            Product product = productRepository.findById(sale.getProductId()).orElse(null);
            if(product.getType().equals(mostPopularType) && sale.getNum()!=0)
                totalOfMostPopularTypeRevenue += sale.getTotal();
        }
        return totalOfMostPopularTypeRevenue;
    }

    //Get total revenue of the second popular type
    public double getTotalOfSecondPopularTypeRevenue(){
        double totalOfSecondPopularTypeRevenue = 0;
        String secondPopular = getSecondPopularType();
        List<Sale> allSales = repository.findAll();
        for(Sale sale : allSales){
            Product product = productRepository.findById(sale.getProductId()).orElse(null);
            if(product.getType().equals(secondPopular) && sale.getNum()!=0)
                totalOfSecondPopularTypeRevenue += sale.getTotal();
        }
        return totalOfSecondPopularTypeRevenue;
    }

    //Get total revenue of the third popular type
    public double getTotalOfThirdPopularTypeRevenue(){
        double totalOfThirdPopularTypeRevenue = 0;
        String thirdPopular = getThirdPopularType();
        List<Sale> allSales = repository.findAll();
        for(Sale sale : allSales){
            Product product = productRepository.findById(sale.getProductId()).orElse(null);
            if(product.getType().equals(thirdPopular) && sale.getNum()!=0)
                totalOfThirdPopularTypeRevenue += sale.getTotal();
        }
        return totalOfThirdPopularTypeRevenue;
    }

    //Get total revenue of other types
    public double getTotalOfOtherTypeRevenue(){
        double totalOfOtherTypeRevenue = 0;
        String mostPopular = getMostPopularType();
        String secondPopular = getSecondPopularType();
        String thirdPopular = getThirdPopularType();
        List<Sale> allSales = repository.findAll();
        for(Sale sale : allSales){
            Product product = productRepository.findById(sale.getProductId()).orElse(null);
            if(product.getType().equals(mostPopular)||product.getType().equals(secondPopular)||product.getType().equals(thirdPopular))
                continue;
            else if(sale.getNum()!=0)
                totalOfOtherTypeRevenue += sale.getTotal();
        }
        return totalOfOtherTypeRevenue;
    }

    //Get the required sale by its id from the repository.
    public Sale getSaleById(int id){
        return repository.findById(id).orElse(null);
    }

    //Get the required sale by its name from the repository.
    public Sale getSaleByName(String name){
        List<Sale> sales = repository.findAllByName(name);
        if(sales.size()==0)
            return null;
        return sales.get(0);
    }

    //Return sales.
    public String returnSale(int id, int returnNum){
        Sale sale = repository.findById(id).orElse(null);//null if not found
        Product product = productRepository.findById(sale.getProductId()).orElse(null);
        if(product!=null) {
            product.setQuantity(product.getQuantity() + returnNum);
            productRepository.save(product);
            return "Sale returned ||" + id;
        }
        else
            return "Product not exist";
    }

    //Find all sales within the start and end date
    public List<Sale> salesWithinGivenDates(String startDate, String endDate){
        List<Sale> allSales = repository.findByDateBetween(startDate,endDate);
        List<Sale> requiredSales = new ArrayList<>();
        for(Sale sale : allSales){
            if(sale.getNum()!=0)
                requiredSales.add(sale);
        }
        return requiredSales;
    }

    //Find all returned sales within the start and end date
    public List<Sale> returnedSalesWithinGivenDates(String startDate, String endDate){
        List<Sale> allSales = repository.findByDateBetween(startDate,endDate);
        List<Sale> requiredSales = new ArrayList<>();
        for(Sale sale : allSales){
            if(sale.getReturnPrice()!=0)
                requiredSales.add(sale);
        }
        return requiredSales;
    }

    //Update the information of the sale in the repository
    public Sale updateSale(Sale sale, String updateType){
        Sale existingSale = repository.findById(sale.getId()).orElse(null);
        int returnNum = sale.getReturnNum();
        if(existingSale!=null){
            sale.setType(existingSale.getType());
            sale.setSupplierName(existingSale.getSupplierName());
            if(updateType.equals("return")) {//return purchase
                sale.setNum(existingSale.getNum()-sale.getReturnNum());
                sale.setReturnNum(sale.getReturnNum() + existingSale.getReturnNum());
            }
            else{//update purchase info
                sale.setNum(sale.getNum());
                sale.setReturnNum(sale.getReturnNum());
            }
            sale.setReturnTotal(sale.getReturnPrice()*sale.getReturnNum());
            sale.setTotal(sale.getPrice()*sale.getNum());
            repository.save(sale);
            returnSale(sale.getId(),returnNum);
            averagePriceService.updateAvgSalePrice(sale);
            return sale;
        }
        return null;
    }

    //Get sales by offset and page size from the repository.
    public List<Sale> findSalesWithPagination(int offset, int pageSize){
        return repository.findByNumGreaterThan(0, PageRequest.of(offset, pageSize));
    }

    //Get return sales by offset and page size from the repository.
    public List<Sale> findReturnSalesWithPagination(int offset, int pageSize){
        return repository.findByReturnPriceGreaterThan(0, PageRequest.of(offset, pageSize));
    }

    //Get sales by offset and page size from the repository and sort them by a given field.
    public List<Sale> findSalesWithPaginationAndSorting(int offset,int pageSize,String field){
        return repository.findByNumGreaterThan(0, PageRequest.of(offset, pageSize).withSort(Sort.Direction.ASC,field));
    }

    //Get return sales by offset and page size from the repository and sort them by a given field.
    public List<Sale> findReturnSalesWithPaginationAndSorting(int offset,int pageSize,String field){
        return repository.findByReturnPriceGreaterThan(0, PageRequest.of(offset, pageSize).withSort(Sort.Direction.ASC,field));
    }

    // Get one day total sale
    public double getOneDaySaleTotal(String date){
        double totalOneDay = 0;
        List<Sale> allSales = repository.findAllByDate(date);
        for (Sale sale : allSales){
            totalOneDay +=sale.getTotal();
        }
        return totalOneDay;
    }

    //Get the total sales record within given dates
    public double getPeriodSaleTotal(String startDate, String endDate){
        double periodTotal = 0;
        List<Sale> allSales = repository.findByDateBetween(startDate,endDate);
        for(Sale sale : allSales){
            periodTotal += sale.getTotal();
        }
        return periodTotal;
    }

    //Find the most popular type of products
    public String getMostPopularType(){
        String mostPopularType = null;
        int maxNumber = 0;
        List<String> types = repository.getDistinctType();
        for(String type : types){
            int total = repository.getTotalNumByType(type);
            if(total > maxNumber){
                maxNumber = total;
                mostPopularType = type;
            }
        }
        return mostPopularType;
    }

    //Find the second most popular type
    public String getSecondPopularType(){
        String mostPopular = getMostPopularType();
        String secondPopularProduct = null;
        int maxNumber = 0;
        List<String> types = repository.getDistinctType();
        types.remove(mostPopular);
        for(String type : types){
            if(type.equals(mostPopular))
                continue;
            int total = repository.getTotalNumByType(type);
            if(total > maxNumber){
                maxNumber = total;
                secondPopularProduct = type;
            }
        }
        return secondPopularProduct;
    }

    //Find the third most popular type
    public String getThirdPopularType(){
        String mostPopular = getMostPopularType();
        String secondPopular = getSecondPopularType();
        String thirdPopularProduct = null;
        int maxNumber = 0;
        List<String> types = repository.getDistinctType();
        for(String type : types){
            if(type.equals(mostPopular)||type.equals(secondPopular))
                continue;
            int total = repository.getTotalNumByType(type);
            if(total > maxNumber){
                maxNumber = total;
                thirdPopularProduct = type;
            }
        }
        return thirdPopularProduct;
    }

    //Get the total number of products of the most popular type
    public int getTotalNumOfMostPopularTypes(String mostPopular){
        return repository.getTotalNumByType(mostPopular);
    }

    //Get the total number of products of the second popular type
    public int getTotalNumOfSecondPopularTypes(String secondPopular){
        return repository.getTotalNumByType(secondPopular);
    }

    //Get the total number of products of the third popular type
    public int getTotalNumOfThirdPopularTypes(String thirdPopular){
        return repository.getTotalNumByType(thirdPopular);
    }

    //Get the total number of products of other types
    public int getTotalNumOfOtherTypes(String mostPopular, String secondPopular, String thirdPopular){
        int totalNum = 0;
        List<String> types = repository.getDistinctType();
        types.remove(mostPopular);
        for(String type : types){
            if(type.equals(mostPopular)||type.equals(secondPopular)||type.equals(thirdPopular))
                continue;
            totalNum += repository.getTotalNumByType(type);
        }
        return totalNum;
    }

    //Find the most popular product
    public String getMostPopularProduct(){
        String mostPopularProduct = null;
        int maxNumber = 0;
        List<Sale> allSales = repository.findAll();
        while(!allSales.isEmpty()){
            int total = 0;
            Sale firstSale = allSales.get(0);
            List<Sale> salesWithSameName = repository.findAllByName(firstSale.getName());
            for(Sale sale : salesWithSameName){
                total += sale.getNum();
            }
            if(total > maxNumber){
                maxNumber = total;
                mostPopularProduct = firstSale.getName();
            }
            allSales.removeAll(salesWithSameName);
        }
        return mostPopularProduct;
    }

    //Find the second most popular product
    public String getSecondPopularProduct(String mostPopular){
        String secondPopularProduct = null;
        int maxNumber = 0;
        List<Sale> allSales = repository.findAll();
        allSales.removeAll(repository.findAllByName(mostPopular));
        while(!allSales.isEmpty()){
            int total = 0;
            Sale firstSale = allSales.get(0);
            List<Sale> salesWithSameName = repository.findAllByName(firstSale.getName());
            for(Sale sale : salesWithSameName){
                total += sale.getNum();
            }
            if(total > maxNumber) {
                maxNumber = total;
                secondPopularProduct = firstSale.getName();
            }
            allSales.removeAll(salesWithSameName);
        }
        return secondPopularProduct;
    }

    //Find the third most popular product
    public String getThirdPopularProduct(String mostPopular, String secondPopular){
        String thirdPopularProduct = null;
        int maxNumber = 0;
        List<Sale> allSales = repository.findAll();
        allSales.removeAll(repository.findAllByName(mostPopular));
        allSales.removeAll(repository.findAllByName(secondPopular));
        while(!allSales.isEmpty()){
            int total = 0;
            Sale firstSale = allSales.get(0);
            List<Sale> salesWithSameName = repository.findAllByName(firstSale.getName());
            for(Sale sale : salesWithSameName){
                total += sale.getNum();
            }
            if(total > maxNumber){
                maxNumber = total;
                thirdPopularProduct = firstSale.getName();
            }
            allSales.removeAll(salesWithSameName);
        }
        return thirdPopularProduct;
    }

    //Find the fourth most popular product
    public String getFourthPopularProduct(String mostPopular, String secondPopular, String thirdPopular){
        String fourthPopularProduct = null;
        int maxNumber = 0;
        List<Sale> allSales = repository.findAll();
        allSales.removeAll(repository.findAllByName(mostPopular));
        allSales.removeAll(repository.findAllByName(secondPopular));
        allSales.removeAll(repository.findAllByName(thirdPopular));
        while(!allSales.isEmpty()){
            int total = 0;
            Sale firstSale = allSales.get(0);
            List<Sale> salesWithSameName = repository.findAllByName(firstSale.getName());
            for(Sale sale : salesWithSameName){
                total += sale.getNum();
            }
            if(total > maxNumber){
                maxNumber = total;
                fourthPopularProduct = firstSale.getName();
            }
            allSales.removeAll(salesWithSameName);
        }
        return fourthPopularProduct;
    }

    //Get the most recent six sales records
    public List<Sale> getRecentSixSales(){
        return repository.findByNumGreaterThan(0, PageRequest.of(0, 6).withSort(Sort.Direction.DESC,"date"));
    }

    //Get the total number of products for a given name from repository.
    public int getProductSaleNum(String productName){
        return repository.getProductSaleNum(productName);
    }
}
