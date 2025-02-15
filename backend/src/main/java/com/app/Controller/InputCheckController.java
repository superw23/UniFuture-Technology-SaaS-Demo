//Made by GRPTeam05
package com.app.Controller;

import com.app.Service.InputCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InputCheckController {
    @Autowired
    private InputCheckService inputCheckService;

    @GetMapping("/checkSupplierExistence")
    public boolean checkSupplierExistence(@RequestParam String supplierName){
        return inputCheckService.checkSupplierExistence(supplierName);
    }

    @GetMapping("/checkCustomerExistence")
    public boolean checkCustomerExistence(@RequestParam String customerName){
        return inputCheckService.checkCustomerExistence(customerName);
    }

    @GetMapping("/checkProductExistence")
    public boolean checkProductExistence(@RequestParam String productName, @RequestParam String supplierName){
        return inputCheckService.checkProductExistence(productName, supplierName);
    }

    @GetMapping("/checkAvailability")
    public boolean checkAvailability(@RequestParam String productName, @RequestParam String supplierName, @RequestParam int saleNum){
        return inputCheckService.checkAvailability(productName, supplierName, saleNum);
    }
}
