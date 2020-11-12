package com.lifevision.HelloSewa.response;

import com.lifevision.HelloSewa.dto.AbstractDto;
import com.lifevision.HelloSewa.utils.WishlistItemStatus;

public class WishlistResponse extends AbstractDto {
	
	private Long productId;
	private WishlistItemStatus status;
	private String name;
	private float price;
	private Long loginId;
	private float sellingPrice;
	private String thumbnail;
	private float discount;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public WishlistItemStatus getStatus() {
		return status;
	}
	public void setStatus(WishlistItemStatus status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	public float getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(float sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
}
