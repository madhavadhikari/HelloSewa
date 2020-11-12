package com.lifevision.HelloSewa.request;

import java.io.Serializable;

public class ProductReviewRatingCreationRequest implements Serializable {
	
	private int rating; 
	
	private String review;

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

}
