package com.lifevision.HelloSewa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifevision.HelloSewa.model.Login;
import com.lifevision.HelloSewa.model.UserCart;
import com.lifevision.HelloSewa.utils.CartItemStatus;

@Repository
public interface UserCartRepository extends JpaRepository<UserCart, Long>{

	List<UserCart> findByLoginIdAndStatusNot(Long loginId, CartItemStatus removed);

	UserCart findByIdAndStatusNot(Long userCartId, CartItemStatus removed);

	UserCart findByIdAndStatus(Long userCartId, CartItemStatus active);

	UserCart findByLoginIdAndProductIdAndStatus(Long loginId, Long productId, CartItemStatus active);

}
