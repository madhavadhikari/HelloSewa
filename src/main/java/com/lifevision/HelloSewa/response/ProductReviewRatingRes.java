package com.lifevision.HelloSewa.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductReviewRatingRes implements Serializable {

	private float averageRating;
	
	List<RatingResponse> ratingResponse = new ArrayList<>();

	public float getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}

	public List<RatingResponse> getRatingResponse() {
		return ratingResponse;
	}

	public void setRatingResponse(List<RatingResponse> ratingResponse) {
		this.ratingResponse = ratingResponse;
	}

}
