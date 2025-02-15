//Made by GRPTeam05
package com.app.Repository;

import com.app.Entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
//Product repository to perform operation(such as find, delete) on the Product object
public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findByNameAndSupplierName(String name, String supplierName);
    List<Product> findAllByName (String name);
    List<Product> findAllByType(String type);
    List<Product> findGroupByName(String name);

    @Query(value = "Select * from product_tbl GROUP BY name", nativeQuery = true)
    List<Product> getProductsByDistinctName(Pageable pageable);
    @Query(value = "Select distinct type from Product_tbl", nativeQuery = true)
    List<String> getDistinctType();

    @Transactional
    public void deleteAllByName(String name);
}
