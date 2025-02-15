//Made by GRPTeam05
package com.app.Repository;

import com.app.Entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {
    Supplier findByCompany(String company);
    List<Supplier> findByCompanyContaining(String company);
}
