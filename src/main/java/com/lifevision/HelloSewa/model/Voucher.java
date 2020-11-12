package com.lifevision.HelloSewa.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.lifevision.HelloSewa.utils.VoucherStatus;

@Entity
public class Voucher extends AbstractEntity {

	private Date voucherExpiryDate;
	
	private String couponCode;
	
	private int discountAmount;
	
	private int discountPercent;
	
	private Long productId;
	
	@Enumerated(EnumType.STRING)
	private VoucherStatus status; 

	public Date getVoucherExpiryDate() {
		return voucherExpiryDate;
	}

	public void setVoucherExpiryDate(Date voucherExpiryDate) {
		this.voucherExpiryDate = voucherExpiryDate;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public int getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(int discountAmount) {
		this.discountAmount = discountAmount;
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public VoucherStatus getStatus() {
		return status;
	} 

	public void setStatus(VoucherStatus status) {
		this.status = status;
	}
}
