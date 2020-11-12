package com.lifevision.HelloSewa.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.lifevision.HelloSewa.utils.DeliveryOption;
import com.lifevision.HelloSewa.utils.OrderStatus;
import com.lifevision.HelloSewa.utils.PaymentMethod;

@Entity(name = "product_order")
public class Order extends AbstractEntity {
	
	private Long orderBy;
	
	private float amount;
	
	private Long shippingAddressId;
	
	private Long billingAddressId;
	
	private String voucher;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private DeliveryOption deliveryOption;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	private float deliveryCost;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
	private  List<OrderItem> items = new ArrayList<>();
	
	public Long getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Long orderBy) {
		this.orderBy = orderBy;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Long getShippingAddressId() {
		return shippingAddressId;
	}
	public void setShippingAddressId(Long shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public float getDeliveryCost() {
		return deliveryCost;
	}
	public void setDeliveryCost(float deliveryCost) {
		this.deliveryCost = deliveryCost;
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
	public Long getBillingAddressId() {
		return billingAddressId;
	}
	public void setBillingAddressId(Long billingAddressId) {
		this.billingAddressId = billingAddressId;
	}
}
