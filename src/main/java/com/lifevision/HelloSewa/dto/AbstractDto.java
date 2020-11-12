package com.lifevision.HelloSewa.dto;

import java.io.Serializable;
import java.util.Date;

public class AbstractDto implements Serializable {
	
	protected Long id; 
	protected Date createdDate;
	protected Date modifiedDate;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
