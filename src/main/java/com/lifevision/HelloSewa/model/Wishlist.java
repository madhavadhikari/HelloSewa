package com.lifevision.HelloSewa.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.lifevision.HelloSewa.utils.WishlistItemStatus;

@Entity
public class Wishlist extends AbstractEntity {
	
	private Long loginId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "productId")
	private Product product;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private WishlistItemStatus status;

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

	public WishlistItemStatus getStatus() {
		return status;
	}

	public void setStatus(WishlistItemStatus status) {
		this.status = status;
	}
	
}

