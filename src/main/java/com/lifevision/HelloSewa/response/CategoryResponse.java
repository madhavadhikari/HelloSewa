package com.lifevision.HelloSewa.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryResponse implements Serializable {
	
	private int id;
	
	private int parentCategoryId;
	
	private String image;
	
	private String name;
	
	private List<SubCategoryResponse> subCategoryResponseList = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(int parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<SubCategoryResponse> getSubCategoryResponseList() {
		return subCategoryResponseList;
	}

	public void setSubCategoryResponseList(List<SubCategoryResponse> subCategoryResponseList) {
		this.subCategoryResponseList = subCategoryResponseList;
	}
	
}
