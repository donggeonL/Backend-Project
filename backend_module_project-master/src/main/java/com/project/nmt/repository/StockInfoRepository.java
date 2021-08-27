package com.project.nmt.repository;


import com.project.nmt.model.Stock;
import com.project.nmt.model.StockInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface StockInfoRepository extends JpaRepository<StockInfo, Long> {
	List<StockInfo> findAllByStock(Stock stock);

	@Query("select s from StockInfo s where s.stock=?1 AND s.infoDate=?2")
	StockInfo findAllByStockAndInfoDate(Stock stock,LocalDate today);//당일의 품목 가격을 확인

	@Query("select s from StockInfo s where s.infoDate = (select max(s.infoDate) from StockInfo s)")
    List<StockInfo> findAllRecentByKeyword();
}
