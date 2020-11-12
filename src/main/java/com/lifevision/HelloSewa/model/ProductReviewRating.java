package com.lifevision.HelloSewa.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.lifevision.HelloSewa.utils.ProductReviewRatingStatus;

@Entity
public class ProductReviewRating extends AbstractEntity {
	
	private Long reviewBy;
	
	private Long productId;
	
	private int rating;
	
	private String review;
	
	@Enumerated(EnumType.STRING)
	private ProductReviewRatingStatus status;
	
	public Long getReviewBy() {
		return reviewBy;
	}
	
	public void setReviewBy(Long reviewBy) {
		this.reviewBy = reviewBy;
	}
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getReview() {
		return review;
	}
	
	public void setReview(String review) {
		this.review = review;
	}
	
	public ProductReviewRatingStatus getStatus() {
		return status;
	}
	
	public void setStatus(ProductReviewRatingStatus status) {
		this.status = status;
	}
	
}
