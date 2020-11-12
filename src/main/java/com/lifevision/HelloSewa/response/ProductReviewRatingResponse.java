package com.lifevision.HelloSewa.response;

import java.util.Date;

import com.lifevision.HelloSewa.utils.ProductReviewRatingStatus;

public class ProductReviewRatingResponse {
	
	private Long Id;

	private Long reviewBy;

	private Long productId;

	private String brandName;

	private  String description;

	private int rating;

	private String name;

	private String thumbnail;

	private String review;
	
	private Date reviewDate;
	
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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