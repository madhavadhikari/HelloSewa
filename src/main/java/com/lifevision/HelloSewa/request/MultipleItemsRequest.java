package com.lifevision.HelloSewa.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lifevision.HelloSewa.utils.DeliveryOption;
import com.lifevision.HelloSewa.utils.PaymentMethod;

public class MultipleItemsRequest implements Serializable {
	
	private List<Long> cartItemIds = new ArrayList<>();
	
	private PaymentMethod paymentMethod;
	
	private String voucher;
	
	private DeliveryOption deliveryOption;

	public List<Long> getCartItemIds() {
		return cartItemIds;
	}

	public void setCartItemIds(List<Long> cartItemIds) {
		this.cartItemIds = cartItemIds;
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
