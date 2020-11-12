package com.lifevision.HelloSewa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifevision.HelloSewa.model.Order;
import com.lifevision.HelloSewa.utils.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order , Long> {

	/**
	 * finds the list of orders(expect the order removed) ordered by given loginId 
	 * @param loginId
	 * @param removed
	 * @return List<Order>
	 */
	List<Order> findByStatusNotAndOrderBy(OrderStatus removed, Long loginId);

}
