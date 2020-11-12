package com.lifevision.HelloSewa.response;

import com.lifevision.HelloSewa.dto.AbstractDto;
import com.lifevision.HelloSewa.utils.CartItemStatus;
import com.lifevision.HelloSewa.utils.WishlistItemStatus;

public class UserCartResponse extends AbstractDto {
	
	private Long productId;
	private CartItemStatus status;
	private int quantity;
	private Long loginId;
	private String name;
	private float price;
	private int stock;
	private float sellingPrice;
	private String thumbnail;
	private float discount;
	private WishlistItemStatus wishlistStatus;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
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
	public CartItemStatus getStatus() {
		return status;
	}
	public void setStatus(CartItemStatus status) {
		this.status = status;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	public WishlistItemStatus getWishlistStatus() {
		return wishlistStatus;
	}
	public void setWishlistStatus(WishlistItemStatus wishlistStatus) {
		this.wishlistStatus = wishlistStatus;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
}
