//Made by GRPTeam05
package com.app.Controller;

import com.app.Entity.Purchase;
import com.app.Service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
//PurchaseController class passes requests from the client and then let the PurchaseService class deals with them.
public class PurchaseController {
    @Autowired
    private PurchaseService service;

    //Add a new purchase
    @PostMapping("/addPurchase")
    public Purchase addPurchase(@RequestBody Purchase purchase){return service.savePurchase(purchase);}

    //Get all purchases record
    @GetMapping("/purchases")
    public List<Purchase> findAllPurchases(){
        return service.getPurchase();
    }

    //Return all purchase list
    @GetMapping("/returnPurchases")
    public List<Purchase> findAllReturnPurchases(){
        return service.getReturnPurchases();
    }

    //Get the required purchase by its name
    @GetMapping("/purchaseByName/{name}")
    public Purchase findPurchaseByName(@PathVariable String name){return service.getPurchaseByName(name);}

    //Get the required purchase by its offset and page size
    @GetMapping("/purchasePagination/{offset}/{pageSize}")
    private List<Purchase> getPurchasesWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        return service.findPurchasesWithPagination(offset, pageSize);
    }

    //Get the return purchase record by offset and page size
    @GetMapping("/returnPurchasePagination/{offset}/{pageSize}")
    private List<Purchase> getReturnPurchasesWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        return service.findReturnPurchasesWithPagination(offset, pageSize);
    }

    //Get the purchase record by offset and page size and sort them by a given field
    @GetMapping("/purchasePaginationAndSort/{offset}/{pageSize}/{field}")
    private List<Purchase> getPurchasesWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String field) {
        return service.findPurchasesWithPaginationAndSorting(offset, pageSize,field);
    }

    //Get the return purchase record by offset and page size and sort them by a given field
    @GetMapping("/returnPurchasePaginationAndSort/{offset}/{pageSize}/{field}")
    private List<Purchase> findReturnPurchasesWithPaginationAndSorting(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String field) {
        return service.findReturnPurchasesWithPaginationAndSorting(offset, pageSize, field);
    }

    //Get the purchase record by start date and end date
    @GetMapping("/purchasesWithinGivenDates/{startDate}/{endDate}")
    public List<Purchase> purchasesWithinGivenDates(@PathVariable String startDate, @PathVariable String endDate){return service.purchasesWithinGivenDates(startDate, endDate);}

    //Get the return purchase record by start date and end date
    @GetMapping("/returnPurchasesWithinGivenDates/{startDate}/{endDate}")
    public List<Purchase> returnPurchasesWithinGivenDates(@PathVariable String startDate, @PathVariable String endDate){return service.returnedPurchasesWithinGivenDates(startDate, endDate);}

    @GetMapping("/purchasesWithDistinctTypes")
    public List<String> purchasesWithDistinctTypes(){
        return service.purchasesWithDistinctTypes();
    }

    //Update the information of the purchase.
    @PutMapping("/updatePurchase")
    public Purchase updatePurchase(@RequestBody Purchase purchase, @RequestParam int updateType){
        return service.updatePurchase(purchase, updateType);
    }
}
