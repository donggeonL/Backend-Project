package com.project.nmt.service;

import com.project.nmt.model.OrderLog;
import com.project.nmt.model.User;
import com.project.nmt.repository.OrderLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderLogService {

    private final OrderLogRepository orderLogRepository;

    public List<OrderLog> getListByUserAndDate(User user, LocalDate startDate, LocalDate endDate) {
        List<OrderLog> list = orderLogRepository.findAllByUser(user);

        return list.stream()
                .filter(x -> x.getSoldDate() != null &&
                             x.getSoldDate().compareTo(startDate) >= 0 &&
                             x.getSoldDate().compareTo(endDate) <= 0)
                .collect(Collectors.toList());
    }

    public List<OrderLog> getListByUser(User user) {
        return orderLogRepository.findAllByUser(user);
    }

}
