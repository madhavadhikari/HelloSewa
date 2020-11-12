package com.lifevision.HelloSewa.response;

import com.lifevision.HelloSewa.dto.AbstractDto;

public class RewardResponse extends AbstractDto {
	
	private Long productId;
	
	private int points;
	
	private Long userId;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
