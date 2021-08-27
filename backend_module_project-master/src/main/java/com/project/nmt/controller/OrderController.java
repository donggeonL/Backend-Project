package com.project.nmt.controller;

import com.project.nmt.model.Order;
import com.project.nmt.model.Stock;
import com.project.nmt.model.StockInfo;
import com.project.nmt.model.User;
import com.project.nmt.service.OrderService;
import com.project.nmt.service.StockInfoService;
import com.project.nmt.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final StockInfoService stockInfoService;
    private final StockService stockService;

    @Transactional
    @ResponseBody
    @GetMapping("/order")
    public Boolean orderProduct(HttpSession session, Long stockId, int flag, int cnt) {//flag 0매수 1매도
        User user = (User) session.getAttribute("user");
        Stock stock = stockService.getStockById(stockId);

        LocalDateTime now = LocalDateTime.now();
        LocalDate today;
        if (now.getHour() < 15) {// 3시에 갱신되기떄문에 이전 시간에는 전날 가격 유지
            today = LocalDate.now().minusDays(1);
        } else {
            today = LocalDate.now();
        }
        //품목에 대한 현재 가격
        StockInfo nowStockInfo = stockInfoService.findAllByStockAndInfoDate(stock, today);

        //현재 거래 금액
        Long totalPrice = ((long) nowStockInfo.getPrice() * cnt);
        //보유 품목에 대한 현황
        Order order = orderService.findByUserAndStock(user, stock);

        if (flag == 0) {//구매
            if (user.getBudget() < totalPrice || stock.getQuantity() < cnt) {
                return false;
            }

            orderService.buy(user, nowStockInfo, order, totalPrice, cnt);
        }
        else {//판매
            if (order == null || order.getQuantity() < cnt) {//가진것이 없거나 수량이 모자라면
                return false;
            }

            orderService.sell(user, nowStockInfo, stock, order, cnt);
        }

        return true;
    }
}
