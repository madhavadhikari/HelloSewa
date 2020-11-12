package com.lifevision.HelloSewa.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.lifevision.HelloSewa.utils.CartItemStatus;

@Entity
public class UserCart extends AbstractEntity {
	
	private int quantity;
	
	private Long loginId;
	
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private CartItemStatus status;
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public CartItemStatus getStatus() {
		return status;
	}

	public void setStatus(CartItemStatus status) {
		this.status = status;
	}

	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
