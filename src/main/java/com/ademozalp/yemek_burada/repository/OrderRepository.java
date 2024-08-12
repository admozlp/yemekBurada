package com.ademozalp.yemek_burada.repository;

import com.ademozalp.yemek_burada.model.Order;
import com.ademozalp.yemek_burada.model.enums.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findAllByStatusIn(List<OrderStatus> statusList, Pageable pageable);
}
