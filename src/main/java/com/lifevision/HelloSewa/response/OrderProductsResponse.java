package com.lifevision.HelloSewa.response;

import java.io.Serializable;

public class OrderProductsResponse implements Serializable{
	
	private Long orderItemId;
	private Long productId;
	private int quantity;
	private float totalPrice;
	private String productName;
	private float productPrice;
	private String thumbnail;
	private int productBrand;
		
	public Long getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public int getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(int productBrand) {
		this.productBrand = productBrand;
	}
}