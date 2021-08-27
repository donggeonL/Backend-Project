package com.project.nmt.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.project.nmt.model.Order;
import com.project.nmt.model.Stock;
import com.project.nmt.model.User;

import java.util.List;

@Transactional
public interface OrderRepository extends JpaRepository<Order, Long>{

	Order findByUserAndStock(User user, Stock stock);

	@Modifying
	@Query("update Order o SET o.price=?3 WHERE o.user=?1 AND o.stock=?2")
	void updateOrderPrice(User user, Stock stock, int price);//보유 내역의 평균가격을 변경
	
	@Modifying
	@Query("delete From Order o  WHERE o.id=:orderId")
	void deleteOrderById(Long orderId);//팔았을떄 보유내역에서 삭제

	@Modifying
	@Query("update Order o SET o.quantity=?2 WHERE o.id=?1")
	void updateQuantityById(Long orderId, int count);//팔았을떄 보유내역에서 감소

	List<Order> findAllByUser(User user);
}
