//Made by GRPTeam05
package com.app.Service;

import com.app.Entity.Product;
import com.app.Entity.Purchase;
import com.app.Entity.Sale;
import com.app.Repository.ProductRepository;
import com.app.Repository.PurchaseRepository;
import com.app.Repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
//AveragePriceService class calculates the average sales and purchase price for a given product.
public class AveragePriceService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SaleRepository saleRepository;

    //Update average purchase price for a given product.
    public void updateAvgPurchasePrice(Purchase purchase){
        Product product = productRepository.findById(purchase.getProductId()).orElse(null);//find the product based on product id
        List<Purchase> productsWithSameId = new ArrayList<>();
        productsWithSameId = purchaseRepository.findAllByProductId(purchase.getProductId());
        int existingNum = 0;//the number of unreturned products in the product repository based on the product id
        double priceGone = 0;
        double avgPurchasePrice = 0;
        for(Purchase p : productsWithSameId){
            priceGone += p.getTotal() - p.getReturnTotal();
            existingNum += p.getNum();
        }
        if(existingNum==0)
            avgPurchasePrice = 0;
        else
            avgPurchasePrice = priceGone / existingNum;
        product.setPurchasePrice(avgPurchasePrice);
        productRepository.save(product);
    }

    //Update average sales price for a given product.
    public void updateAvgSalePrice(Sale sale){
        Product product = productRepository.findById(sale.getProductId()).orElse(null);//find the product based on product id
        List<Sale> productsWithSameId = new ArrayList<>();
        productsWithSameId = saleRepository.findAllByProductId(sale.getProductId());
        int existingNum = 0;//the number of unreturned products in the product repository based on the product id
        double priceGained = 0;
        double avgSalePrice = 0;
        for(Sale s : productsWithSameId){
            priceGained += s.getTotal() - s.getReturnTotal();
            existingNum += s.getNum();
        }
        if(existingNum==0)
            avgSalePrice = 0;
        else
            avgSalePrice = priceGained / existingNum;
        product.setSellPrice(avgSalePrice);
        productRepository.save(product);
    }
}
