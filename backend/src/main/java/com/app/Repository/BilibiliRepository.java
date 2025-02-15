package com.app.Repository;

import com.app.Entity.Bilibili;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BilibiliRepository extends JpaRepository<Bilibili,Integer> {
    Bilibili findBy转入单号(String 转入单号);
    List<Bilibili> findBy转入时间Between(String startDate, String endDate);
    List<Bilibili> findAllBy来源(String userName);
    @Query(value = "Select distinct 来源 from Bilibili", nativeQuery = true)
    List<String> getDistinct来源();
}
