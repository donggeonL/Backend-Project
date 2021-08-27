package com.project.nmt.repository;

import com.project.nmt.model.OrderLog;
import com.project.nmt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLogRepository extends JpaRepository<OrderLog, Long> {

    List<OrderLog> findAllByUser(User user);
}
