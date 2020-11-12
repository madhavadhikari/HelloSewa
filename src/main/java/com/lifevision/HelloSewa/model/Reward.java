package com.lifevision.HelloSewa.model;

import javax.persistence.Entity;

@Entity
public class Reward extends AbstractEntity{

	private Long productId;
	
	private int points;
	
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
