package com.project.nmt.controller;

import com.project.nmt.model.OrderLog;
import com.project.nmt.model.Stock;
import com.project.nmt.model.StockInfo;
import com.project.nmt.model.User;
import com.project.nmt.repository.UserRepository;
import com.project.nmt.service.ChartService;
import com.project.nmt.service.OrderLogService;
import com.project.nmt.service.StockInfoService;
import com.project.nmt.service.StockService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class StockController {

    private final StockService stockService;
    private final StockInfoService stockInfoService;
    private final OrderLogService orderLogService;
    private final UserRepository userRepository;
    private final ChartService chartService;

    @GetMapping("/stock")
    public String getStockMain(Model model) {
        List<StockInfo> recentInfoList = stockInfoService.getRecentData();

        model.addAttribute("infoList", recentInfoList);

        return "stock/stock-main";
    }

    @GetMapping("/stock/transaction/{id}")
    public String getStockTransactionHistory(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                             @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate endDate,
                                             @PathVariable("id") Long id,
                                             Model model) {

        User user = userRepository.findById(id).orElseGet(User::new);
        List<OrderLog> list = orderLogService.getListByUserAndDate(user, startDate, endDate);

        model.addAttribute("user", user);
        model.addAttribute("orderLogList", list);

        return "stock/stock-transaction";
    }


    @GetMapping("/stock/chart/{name}")
    public String nowProduct(@PathVariable("name") String name, Model model, HttpSession session) {
        Stock stock = stockService.getStockByName(name);

        model.addAttribute("stock", stock);

        // 당일 가격, 하루전과의 변동률
        LocalTime now = LocalTime.now();
        LocalDate today;
        String opt = "";

        if (now.getHour() < 15) {// 2시인가 3시에 갱신되기떄문에 이전 시간에는 전날 가격 유지
            today = LocalDate.now().minusDays(1);
            opt = "금일 가격 갱신전입니다.";
        } else {
            today = LocalDate.now();
        }
        model.addAttribute("opt", opt);

        //현재가격과 전날가격과의 변동사항
        StockInfo nowStockInfo = stockInfoService.findAllByStockAndInfoDate(stock, today);
        Long todayPrice = (long) nowStockInfo.getPrice();
        model.addAttribute("todayPrice", todayPrice);

        Long yesterdayPrice = (long) stockInfoService.findAllByStockAndInfoDate(stock, today).getPrice();
        double diff = (todayPrice - yesterdayPrice) / (double) yesterdayPrice;
        String temp = "";
        if (diff >= 0)
            temp = "+";

        String diffPer = String.format(temp + "%.2f", diff * 100);
        model.addAttribute("diffPer", diffPer);

        return "stock/stock-chart";
    }

    @GetMapping("/chart")
    @ResponseBody
    public ResponseEntity<JSONObject> ChartInit(@RequestParam("id") Long id) {//차트를 띄우기 위한 데이터 요청
        Stock stock = stockService.getStockById(id);

        // 차트정보를 json데이터로 전달
        return chartService.json_get(stock);
    }
}
