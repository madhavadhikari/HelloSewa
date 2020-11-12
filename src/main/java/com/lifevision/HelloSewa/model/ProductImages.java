package com.lifevision.HelloSewa.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity

public class ProductImages extends AbstractEntity{
	
	@ManyToOne
	@JoinColumn(name="product_id", nullable=false)
	private Product  product;
	
	private String link;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
		
}
