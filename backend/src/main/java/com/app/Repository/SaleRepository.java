//Made by GRPTeam05
package com.app.Repository;

import com.app.Entity.Sale;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Integer> {
    List<Sale> findAllByDate(String date);
    List<Sale> findAllByName(String name);
    List<Sale> findAllByProductId(int product_id);
    List<Sale> findByDateBetween(String startDate, String endDate);
    List<Sale> findByNumGreaterThan(int num, PageRequest pageRequest);
    List<Sale> findByReturnPriceGreaterThan(double price, PageRequest pageRequest);
    @Query(value = "select SUM(num) from sale_tbl where name=:name", nativeQuery = true)
    int getProductSaleNum(@Param("name") String name);
    @Query(value = "select SUM(num) from sale_tbl where type=:type", nativeQuery = true)
    int getTotalNumByType(@Param("type") String type);
    @Query(value = "Select distinct type from Sale_tbl", nativeQuery = true)
    List<String> getDistinctType();
}
