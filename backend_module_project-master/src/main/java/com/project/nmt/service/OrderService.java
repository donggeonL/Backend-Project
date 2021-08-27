package com.project.nmt.service;

import com.project.nmt.model.*;
import com.project.nmt.repository.OrderLogRepository;
import com.project.nmt.repository.OrderRepository;
import com.project.nmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@Service
public class OrderService {

    private final StockService stockService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderLogRepository orderLogRepository;

    public void buy(User user, StockInfo stockInfo, Order order, Long totalPrice, int buyQuantity) {
        // 매수한 금액만큼 계좌에서 차감
        int updateBudget = (int) (user.getBudget() - totalPrice);
        int afterAvgPrice = (int) (totalPrice / buyQuantity);
        int totalCnt = buyQuantity;
        Stock stock = stockInfo.getStock();

        user.updateBudget(updateBudget);

        if (order != null) {
            totalCnt = order.getQuantity();
            int orderTotalPrice = order.getPrice() * totalCnt;
            // 매수후 평가금액
            afterAvgPrice = (int) ((orderTotalPrice + totalPrice) / (totalCnt + buyQuantity));

            // 기존의 order 삭제
            orderRepository.deleteById(order.getId());

            totalCnt += buyQuantity;
        }

        // 이전 주문내역과 현재 매수로 갱신한 새로운 order삽입
        Order newOrder = Order.builder()
                .user(user)
                .stock(stock)
                .price(afterAvgPrice)
                .boughtDate(LocalDate.now())
                .quantity(totalCnt)
                .build();

        stockService.changeCnt(stock.getId(), -buyQuantity);
        userRepository.save(user);
        orderRepository.save(newOrder);
    }

    public void sell(User user, StockInfo stockInfo, Stock nowStock, Order order, int cnt) {
        if (order.getQuantity() == cnt) {// 판매 개수와 보유 개수가 같으면 삭제
            orderRepository.deleteOrderById(order.getId());
        }
        else {// 보유개수가 더 많으면 차감
            orderRepository.updateQuantityById(order.getId(), order.getQuantity() - cnt);
        }

        // 현재 가격 기준 판매한 금액만큼 계좌에 합산
        user.updateBudget(stockInfo.getPrice() * cnt + user.getBudget());

        // 판것에 대해 log
        OrderLog orderLog = OrderLog.builder()
                .soldDate(LocalDate.now())
                .soldPrice(stockInfo.getPrice())
                .boughtPrice(order.getPrice())
                .soldQuantity(cnt)
                .user(user)
                .stock(nowStock)
                .build();

        stockService.changeCnt(nowStock.getId(), cnt);

        orderLogRepository.save(orderLog);
        userRepository.save(user);
    }

    public Order findByUserAndStock(User user, Stock stock) {
        return orderRepository.findByUserAndStock(user, stock);
    }

    public List<Order> getListByUser(User user) {
        return orderRepository.findAllByUser(user);
    }
}
