//Made by GRPTeam05
package com.app.Repository;

import com.app.Entity.Purchase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
//Purchase repository to perform operation(such as find, delete) on the Purchase object
public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {
    List<Purchase> findAllByName(String name);
    List<Purchase> findByNameContaining(String name);
    List<Purchase> findAllByProductId(int product_id);
    List<Purchase> findByDateBetween(String startDate, String endDate);
    List<Purchase> findByNumGreaterThan(int price, PageRequest pageRequest);
    List<Purchase> findByReturnPriceGreaterThan(double price, PageRequest pageRequest);
}
