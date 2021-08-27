package com.project.nmt.repository;

import com.project.nmt.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("select s.keyword from Stock s")
    List<String> findAllStockKeyword();
    
    Stock findByKeyword(String keyword);

    Stock findByName(String name);

}
