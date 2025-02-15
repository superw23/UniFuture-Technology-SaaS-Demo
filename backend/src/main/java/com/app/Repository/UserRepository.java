//Made by GRPTeam05
package com.app.Repository;

import com.app.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//User repository to perform operation(such as find, delete) on the user object
public interface UserRepository extends JpaRepository<User,Integer> {
    User findBy姓名(String name);
    List<User> findAllBy姓名(String name);
}

