//Made by GRPTeam05
package com.app.Controller;

import com.app.Entity.Sale;
import com.app.Service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
//saleController class passes requests from the client and then let the saleService class deals with them.
public class SaleController {
    @Autowired
    private SaleService service;

    //Add a new sale
    @PostMapping("/addSale")//we can add a sale in Postman, using json format
    public Sale addSale(@RequestBody Sale sale){return service.saveSale(sale);}

    //get all sales records
    @GetMapping("/sales")
    public List<Sale> findAllSales(){
        return service.getSales();
    }

    //Get all returned sales records
    @GetMapping("/returnSales")
    public List<Sale> findAllReturnSales(){
        return service.getReturnSales();
    }

    @GetMapping("/totalOfMostPopularTypeRevenue")
    public double getTotalOfMostPopularTypeRevenue(){
        return service.getTotalOfMostPopularTypeRevenue();
    }

    @GetMapping("/totalOfSecondPopularTypeRevenue")
    public double getTotalOfSecondPopularTypeRevenue(){
        return service.getTotalOfSecondPopularTypeRevenue();
    }

    @GetMapping("/totalOfThirdPopularTypeRevenue")
    public double getTotalOfThirdPopularTypeRevenue(){
        return service.getTotalOfThirdPopularTypeRevenue();
    }

    @GetMapping("/totalOfOtherTypeRevenue")
    public double getTotalOfOtherTypeRevenue(){
        return service.getTotalOfOtherTypeRevenue();
    }

    //Get the required sales by its id
    @GetMapping("/saleById/{id}")
    public Sale findSaleById(@PathVariable int id){return service.getSaleById(id);}

    //Get the required sales by its name
    @GetMapping("/saleByName/{name}")
    public Sale findSaleByName(@PathVariable String name){return service.getSaleByName(name);}

    //Get the required sales by its offset and page size
    @GetMapping("/salePagination/{offset}/{pageSize}")
    private List<Sale> getSalesWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        return service.findSalesWithPagination(offset, pageSize);
    }

    @GetMapping("/returnSalePagination/{offset}/{pageSize}")
    private List<Sale> getReturnSalesWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        return service.findReturnSalesWithPagination(offset, pageSize);
    }

    //Get the return sales record by offset and page size and sort them by a given field
    @GetMapping("/salePaginationAndSort/{offset}/{pageSize}/{field}")//get sales for a given type
    private List<Sale> getSalesWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String field) {
        return service.findSalesWithPaginationAndSorting(offset, pageSize, field);
    }

    @GetMapping("/returnSalePaginationAndSort/{offset}/{pageSize}/{field}")//get returned sales for a given type
    private List<Sale> getReturnSalesWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String field) {
        return service.findReturnSalesWithPaginationAndSorting(offset, pageSize, field);
    }

    @GetMapping("/salesWithinGivenDates/{startDate}/{endDate}") //get sales with a given date
    public List<Sale> salesWithinGivenDates(@PathVariable String startDate, @PathVariable String endDate){return service.salesWithinGivenDates(startDate, endDate);}

    @GetMapping("/returnSalesWithinGivenDates/{startDate}/{endDate}") //get sales with a given date
    public List<Sale> returnSalesWithinGivenDates(@PathVariable String startDate, @PathVariable String endDate){return service.returnedSalesWithinGivenDates(startDate, endDate);}

    @GetMapping("/oneDaySale/{date}")
    public double oneDaySaleTotal(@PathVariable String date){
        return service.getOneDaySaleTotal(date);
    }

    @GetMapping("/periodSaleTotal/{startDate}/{endDate}")
    public double periodSaleTotal(@PathVariable String startDate, @PathVariable String endDate){
        return service.getPeriodSaleTotal(startDate,endDate);
    }

    //Get the most popular type of products
    @GetMapping("/mostPopularType")
    public String getMostPopularType(){
        return service.getMostPopularType();
    }

    //Get the second popular type of products
    @GetMapping("/secondPopularType")
    public String getSecondPopularType(){
        return service.getSecondPopularType();
    }

    //Get the most popular type of products
    @GetMapping("/thirdPopularType")
    public String getThirdPopularType(){
        return service.getThirdPopularType();
    }

    //Get the number of products of the most popular type
    @GetMapping("/mostPopularTypeNum")
    public int getTotalNumOfMostPopularTypes(@RequestParam String mostPopular){
        return service.getTotalNumOfMostPopularTypes(mostPopular);
    }

    //Get the number of products of the second popular type
    @GetMapping("/secondPopularTypeNum")
    public int getTotalNumOfSecondPopularTypes(@RequestParam String secondPopular){
        return service.getTotalNumOfSecondPopularTypes(secondPopular);
    }

    //Get the number of products of the most popular type
    @GetMapping("/thirdPopularTypeNum")
    public int getTotalNumOfThirdPopularTypes(@RequestParam String thirdPopular){
        return service.getTotalNumOfThirdPopularTypes(thirdPopular);
    }

    //Get the number of remaining products of the remaining types
    @GetMapping("/otherTypeNum")
    public int getTotalNumOfOtherTypes(@RequestParam String mostPopular, @RequestParam String secondPopular, @RequestParam String thirdPopular){
        return service.getTotalNumOfOtherTypes(mostPopular, secondPopular, thirdPopular);
    }

    //Get the most popular product(same-name product with the highest sale price in total)
    @GetMapping("/mostPopularProduct")
    public String getMostPopularProduct(){
        return service.getMostPopularProduct();
    }

    //Get the most popular product(same-name product with the highest sale price in total)
    @GetMapping("/secondPopularProduct")
    public String getSecondPopularProduct(@RequestParam String mostPopular){
        return service.getSecondPopularProduct(mostPopular);
    }

    //Get the most popular product(same-name product with the highest sale price in total)
    @GetMapping("/thirdPopularProduct")
    public String getThirdPopularProduct(@RequestParam String mostPopular, @RequestParam String secondPopular){
        return service.getThirdPopularProduct(mostPopular, secondPopular);
    }

    //Get the most popular product(same-name product with the highest sale price in total)
    @GetMapping("/fourthPopularProduct")
    public String getFourthPopularProduct(@RequestParam String mostPopular, @RequestParam String secondPopular, @RequestParam String thirdPopular){
        return service.getFourthPopularProduct(mostPopular, secondPopular, thirdPopular);
    }

    //Get the most recent six sales records
    @GetMapping("/recentSixSales")
    public List<Sale> getRecentSixSales(){
        return service.getRecentSixSales();
    }

    @GetMapping("/numberOfProductSale/{productName}")
    public int getProductSaleNum(@PathVariable String productName){
        return service.getProductSaleNum(productName);
    }

    //Update the information of the sales.
    @PutMapping("/updateSale")
    public Sale updateSale(@RequestBody Sale sale, @RequestParam String updateType){
        return service.updateSale(sale, updateType);
    }
}
