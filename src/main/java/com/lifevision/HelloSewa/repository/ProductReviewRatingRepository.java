package com.lifevision.HelloSewa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifevision.HelloSewa.model.ProductReviewRating;
import com.lifevision.HelloSewa.utils.ProductReviewRatingStatus;

@Repository
public interface ProductReviewRatingRepository extends JpaRepository<ProductReviewRating, Long>{

	List<ProductReviewRating> findByReviewByAndStatusNot(Long loginId, ProductReviewRatingStatus removed);

	List<ProductReviewRating> findByProductId(Long productId);

}
