package com.lifevision.HelloSewa.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import com.lifevision.HelloSewa.utils.ProductStatus;

@Entity
public class Product extends AbstractEntity {
	
	private String name;
	
	private int stock;
	
	private float price;
	
	private float sellingPrice;
	
	private int brandId;
	
	private String description;
	
	private float discount;
	
	private String thumbnail;
	
	@Enumerated(EnumType.STRING)
	private ProductStatus status;
	
	private Long addedBy;
	
	@OneToMany(mappedBy = "product")
	private List<ProductImages> images = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getStock() {
		return stock;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	public float getSellingPrice() {
		return sellingPrice;
	}
	
	public void setSellingPrice(float sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public float getDiscount() {
		return discount;
	}
	
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}
	
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public ProductStatus getStatus() {
		return status;
	}
	
	public void setStatus(ProductStatus status) {
		this.status = status;
	}
	
	public Long getAddedBy() {
		return addedBy;
	}
	
	public void setAddedBy(Long addedBy) {
		this.addedBy = addedBy;
	}

	public List<ProductImages> getImages() {
		return images;
	}

	public void setImages(List<ProductImages> images) {
		this.images = images;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	
}

