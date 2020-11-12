package com.lifevision.HelloSewa.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubSubCategoryResponse implements Serializable{

	private int id;
	
	private int parentCategoryId;
	
	private String image;
	
	private String name;
	
	private List<SubSubSubCategoryResponse> subSubSubCategoryResponse = new ArrayList<>();

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

	public List<SubSubSubCategoryResponse> getSubSubSubCategoryResponse() {
		return subSubSubCategoryResponse;
	}

	public void setSubSubSubCategoryResponse(List<SubSubSubCategoryResponse> subSubSubCategoryResponse) {
		this.subSubSubCategoryResponse = subSubSubCategoryResponse;
	}
	
	
}
