package com.lifevision.HelloSewa.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.lifevision.HelloSewa.utils.DefaultStatus;
import com.lifevision.HelloSewa.utils.ShippingAddressStatus;

@Entity
public class ShippingAddress extends AbstractEntity{
 
	private Long createdBy;
	
	private String receiverName;
	
	private String receiverPhone;
	
	private String district; 
	
	private String city;
	
	private String address;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private DefaultStatus defaultStatus;
	
	@Enumerated(EnumType.STRING)
	private ShippingAddressStatus status;
	
	
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

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

	public ShippingAddressStatus getStatus() {
		return status;
	}

	public void setStatus(ShippingAddressStatus status) {
		this.status = status;
	}

	public DefaultStatus getDefaultStatus() {
		return defaultStatus;
	}

	public void setDefaultStatus(DefaultStatus defaultStatus) {
		this.defaultStatus = defaultStatus;
	}
}
