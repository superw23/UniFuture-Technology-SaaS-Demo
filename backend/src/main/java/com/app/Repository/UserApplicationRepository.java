package com.app.Repository;

import com.app.Entity.User;
import com.app.Entity.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Classname UserApplicationRepository
 * @Description TODO
 * @Date 16/8/2023 下午2:32
 * @Created by SuperW
 */

@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication,Integer> {
    User findBy姓名(String name);
    List<User> findAllBy姓名(String name);
}