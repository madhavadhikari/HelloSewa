package com.lifevision.HelloSewa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lifevision.HelloSewa.request.MultipleItemsRequest;
import com.lifevision.HelloSewa.request.ProductOrderItemInfo;
import com.lifevision.HelloSewa.response.ListingMyOrderResponse;
import com.lifevision.HelloSewa.response.OrderItemResponse;
import com.lifevision.HelloSewa.service.OrderService;

import io.swagger.annotations.ApiOperation;

@Controller
@RestController
@RequestMapping("api/v1/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/single-product")
	@ApiOperation(
			value ="API to order single product", 
			notes = "The api should be used to add a single product ie direct order a product without adding to cart.")
	public ResponseEntity<Object> singleOrder(@RequestHeader Long loginId,
			@RequestHeader String token,
			@RequestBody ProductOrderItemInfo itemInfo,
			@RequestHeader Long shippingId,
			@RequestHeader Long billingId){
		Long id = orderService.singleOrder(loginId, token, itemInfo, shippingId, billingId);
		return new ResponseEntity<Object>("Order Created, Id = "+id, HttpStatus.OK);
	}
	
	@PostMapping("/multiple-product")
	@ApiOperation(
			value ="API to order multiple product",
			notes = "The api should be used to add multiple product ie, order the products that are in the user cart.")
	public ResponseEntity<Object> multipleProductOrder(@RequestHeader Long loginId,
			@RequestHeader String token,
			@RequestBody MultipleItemsRequest req, 
			@RequestHeader Long shippingId,
			@RequestHeader Long billingId){
		Long id = orderService.multipleProductOrder(loginId, token, req, shippingId, billingId);
		return new ResponseEntity<Object>("Order Created, Id = "+id, HttpStatus.OK);
	}
	
	@GetMapping("/view")
	@ApiOperation(
			value ="API to list all my orders",
			notes="The api lists all the order for a user(like order history), expect the order removed by the user from their history.",
			response = ListingMyOrderResponse.class, 
			responseContainer = "List")
	public ResponseEntity<Object> listMyOrders(@RequestHeader Long loginId, @RequestHeader String token){
		List<ListingMyOrderResponse> response = orderService.listMyOrder(loginId, token);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{orderId}/items")
	@ApiOperation(
			value ="API to list particular order items",
			notes="The api lists all the products in an order for a user, expect the products that are removed by the user from that order.",
			response = OrderItemResponse.class)
	public ResponseEntity<Object> listMyOrderItems(@PathVariable Long orderId,
			@RequestHeader Long loginId,
			@RequestHeader String token){
		OrderItemResponse response = orderService.listMyOrderItems(orderId, loginId, token);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	
	@PutMapping("/{orderId}/complete")	
	@ApiOperation(
			value ="API to confirm Order Delivery Completion",
			notes="The api updates the status of an Order when delivery is completed(In-progress order is updated as completed when delivery of order is done).")
	public ResponseEntity<Object> completeOrder(@PathVariable Long orderId, 
			@RequestHeader Long loginId, @RequestHeader String token){
		orderService.completeOrder(orderId, loginId, token);
		return new ResponseEntity<Object> (HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/cancel")	
	@ApiOperation(
			value ="API to CANCEL an order in-progress",
			notes="The api updates the order(in-progress order) status as canceled before the delivery.")
	public ResponseEntity<Object> cancelOrder(@PathVariable Long orderId, 
			@RequestHeader Long loginId, @RequestHeader String token){
		orderService.cancelOrder(orderId, loginId, token);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/remove")	
	@ApiOperation(
			value ="API to delete/remove an completed-order",
			notes="The api removes the order for the given user from the order history. The orders are all Completed ie, devlivered orders.")
	public ResponseEntity<Object> removeOrder(@PathVariable Long orderId, 
			@RequestHeader Long loginId, @RequestHeader String token){
		orderService.removeOrder(orderId, loginId, token);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/cancel-orderitems/{orderItemId}")
	@ApiOperation(
			value ="API to cancel an order items",
			notes="The api cancels the order items for the given order from the order table.")
	public ResponseEntity<Object> cancelOrderItems(@RequestHeader Long loginId, @RequestHeader String token,
			@PathVariable Long orderId, @PathVariable Long orderItemId){
		orderService.cancelOrderItems(loginId, token, orderId, orderItemId);
		return new ResponseEntity<Object>("item canceled sucessfully.", HttpStatus.OK);
	}	
}
