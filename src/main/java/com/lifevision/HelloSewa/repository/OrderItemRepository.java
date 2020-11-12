package com.lifevision.HelloSewa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifevision.HelloSewa.model.Order;
import com.lifevision.HelloSewa.model.OrderItem;
import com.lifevision.HelloSewa.utils.OrderItemStatus;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	List<OrderItem> findByOrderAndStatus(Order order, OrderItemStatus active);

}
