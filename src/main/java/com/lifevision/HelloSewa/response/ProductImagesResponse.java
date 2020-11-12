package com.lifevision.HelloSewa.response;

import com.lifevision.HelloSewa.dto.AbstractDto;

public class ProductImagesResponse extends AbstractDto {
	
	private String link;
	private Long productId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	
	
	

}
