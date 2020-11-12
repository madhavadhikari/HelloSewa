package com.lifevision.HelloSewa.response;

import java.io.Serializable;

import com.lifevision.HelloSewa.utils.DefaultStatus;

public class ShippingAddressResponse implements Serializable {
	
	private Long id;
	
	private Long createdBy; 
	
	private String receiverName;
	
	private String receiverPhone;
	
	private String district; 
	
	private String city;
	
	private DefaultStatus defaultStatus;
	
	private String address;

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public DefaultStatus getDefaultStatus() {
		return defaultStatus;
	}

	public void setDefaultStatus(DefaultStatus defaultStatus) {
		this.defaultStatus = defaultStatus;
	}
	
}
