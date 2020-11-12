package com.lifevision.HelloSewa.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.lifevision.HelloSewa.utils.DeliveryOption;
import com.lifevision.HelloSewa.utils.OrderStatus;
import com.lifevision.HelloSewa.utils.PaymentMethod;

public class OrderItemResponse implements Serializable {

	private Long orderId;
	private Date orderedDate;
	private float orderTotal;
	private OrderStatus orderStatus;
	private Long billinngAddressId;
	private Long shippingAddressId;
	private PaymentMethod paymentMethod;
	private DeliveryOption deliveryOption;
	
	private List<OrderProductsResponse> orderProductsResponse = new ArrayList<>();

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(Date orderedDate) {
		this.orderedDate = orderedDate;
	}

	public float getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(float orderTotal) {
		this.orderTotal = orderTotal;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getBillinngAddressId() {
		return billinngAddressId;
	}

	public void setBillinngAddressId(Long billinngAddressId) {
		this.billinngAddressId = billinngAddressId;
	}

	public Long getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(Long shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public DeliveryOption getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(DeliveryOption deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public List<OrderProductsResponse> getOrderProductsResponse() {
		return orderProductsResponse;
	}

	public void setOrderProductsResponse(List<OrderProductsResponse> orderProductsResponse) {
		this.orderProductsResponse = orderProductsResponse;
	}
}