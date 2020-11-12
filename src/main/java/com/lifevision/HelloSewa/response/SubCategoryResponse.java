package com.lifevision.HelloSewa.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryResponse implements Serializable {

	private int id;
	
	private int parentCategoryId;
	
	private String image;
	
	private String name;
	
	private List<SubSubCategoryResponse> subSubCategoryResponseList = new ArrayList<>();

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SubSubCategoryResponse> getSubSubCategoryResponseList() {
		return subSubCategoryResponseList;
	}

	public void setSubSubCategoryResponseList(List<SubSubCategoryResponse> subSubCategoryResponseList) {
		this.subSubCategoryResponseList = subSubCategoryResponseList;
	}
	
}
