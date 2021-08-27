package com.project.nmt.controller;

import com.project.nmt.dto.OrderDto;
import com.project.nmt.model.Order;
import com.project.nmt.model.Stock;
import com.project.nmt.model.User;
import com.project.nmt.service.OrderService;
import com.project.nmt.service.StockInfoService;
import com.project.nmt.service.StockService;
import com.project.nmt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Controller
public class BudgetController {


    private final UserService userService;
    private final OrderService orderService;
    private final StockService stockService;
    private final StockInfoService stockInfoService;


    @GetMapping("/user/wallet/{id}")
    public String budget(@PathVariable("id") Long id, Model model) {
        User user = userService.getOneById(id);
        List<Order> orderList = orderService.getListByUser(user);
        List<String> keywords = stockService.getStockKeywordList();

        model.addAttribute("user", user);
        model.addAttribute("keywordList", keywords);

        if (orderList == null) {
            return "user/wallet";
        }

        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();
        if (now.getHour() < 15) {// 2시인가 3시에 갱신되기떄문에 이전 시간에는 전날 가격 유지
            today = LocalDate.now().minusDays(1);
        }

        List<OrderDto> list = new ArrayList<>();
        int total = 0;
        for (Order order : orderList) {
            OrderDto temp = new OrderDto();
            temp.setName(order.getStock().getName());
            temp.setBoughtPrice(order.getPrice());
            temp.setQuantity(order.getQuantity());
            temp.setNowPrice(stockInfoService.findAllByStockAndInfoDate(order.getStock(), today).getPrice());
            temp.setKeyword(order.getStock().getKeyword());

            double diff = (temp.getNowPrice() - temp.getBoughtPrice()) / (double) temp.getBoughtPrice();
            String s = "";
            if (diff >= 0) {
                s = "+";
            }

            temp.setPercent(String.format(s + "%.2f", diff * 100));
            temp.setStockId(stockService.getStockByKeyword(temp.getKeyword()).getId());
            list.add(temp);

            total += order.getPrice() * order.getQuantity();
        }

        model.addAttribute("orderDtoList", list);
        model.addAttribute("userTotalBudget", total);

        return "user/wallet";
    }

    @GetMapping("/searchProduct")
    public String searchProduct(@RequestParam("productName") String keyword) {
        Stock now = stockService.getStockByKeyword(keyword);

        return "redirect:/stock/chart/" + now.getName();
    }

}