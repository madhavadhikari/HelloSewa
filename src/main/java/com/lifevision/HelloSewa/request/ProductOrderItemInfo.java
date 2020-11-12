package com.lifevision.HelloSewa.request;

import java.io.Serializable;

import com.lifevision.HelloSewa.utils.DeliveryOption;
import com.lifevision.HelloSewa.utils.PaymentMethod;

public class ProductOrderItemInfo implements Serializable{
	
	private Long productId;
	private int quantity;
	private PaymentMethod paymentMethod;
	private String voucher;
	private DeliveryOption deliveryOption;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getVoucher() {
		return voucher;
	}
	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
	public DeliveryOption getDeliveryOption() {
		return deliveryOption;
	}
	public void setDeliveryOption(DeliveryOption deliveryOption) {
		this.deliveryOption = deliveryOption;
	}
	
}
