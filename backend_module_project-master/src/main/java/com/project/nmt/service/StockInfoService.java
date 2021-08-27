package com.project.nmt.service;

import com.project.nmt.model.Stock;
import com.project.nmt.model.StockInfo;
import com.project.nmt.repository.StockInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StockInfoService {

    private final StockInfoRepository stockInfoRepository;

    public List<StockInfo> getRecentData() {

        return stockInfoRepository.findAllRecentByKeyword();
    }

    public StockInfo findAllByStockAndInfoDate(Stock stock, LocalDate today) {
        return stockInfoRepository.findAllByStockAndInfoDate(stock, today);
    }
}
