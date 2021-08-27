package com.project.nmt.scheduler;

import com.project.nmt.dataSetting.DataSetter;
import com.project.nmt.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Scheduler {

    private final ArticleService articleService;
    private final DataSetter dataSetter;

    // 2시간 마다 기사를 긁어옴.
    @Scheduled(fixedRate = 7200000)
    public void articleUpdate() {
        articleService.scrapAll();
    }

    @Scheduled(cron = "0 0 15 * * *")
    public void getNewStockDate() {
        dataSetter.getTodayProductData();
    }

}
