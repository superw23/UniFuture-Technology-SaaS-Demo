//Made by GRPTeam05
package com.app.Repository;

import com.app.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//Customer repository to perform operation(such as find, delete) on the customer object
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByName(String name);
}
