package com.lifevision.HelloSewa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifevision.HelloSewa.model.Wishlist;
import com.lifevision.HelloSewa.utils.WishlistItemStatus;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long>{

	List<Wishlist> findByLoginIdAndStatusNot(Long loginId, WishlistItemStatus removed);

	Wishlist findByLoginIdAndProductIdAndStatusNot(Long loginId, Long productId, WishlistItemStatus removed);

	Wishlist findByLoginIdAndProductIdAndStatus(Long loginId, Long id, WishlistItemStatus active);

	Wishlist findByLoginIdAndProductId(Long loginId, Long productId);

}
