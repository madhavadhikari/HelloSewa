package com.lifevision.HelloSewa.response;

import java.util.ArrayList;
import java.util.List;
import com.lifevision.HelloSewa.dto.AbstractDto;

public class ProductResponse extends AbstractDto {
	
	private String name;
	private int stock;
	private float price;
	private float sellingPrice;
	private int brandId;
	private int categoryId;
	private String description;
	private float discount;
	private String thumbnail;
	private float savedAmmount;
	private Long addedBy;
	private List<ProductImagesResponse> images = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStock() {
		return stock;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
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
	public Long getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(Long addedBy) {
		this.addedBy = addedBy;
	}
	public List<ProductImagesResponse> getImages() {
		return images;
	}
	public void setImages(List<ProductImagesResponse> images) {
		this.images = images;
	}
	public float getSavedAmmount() {
		return savedAmmount;
	}
	public void setSavedAmmount(float savedAmmount) {
		this.savedAmmount = savedAmmount;
	}	
}

