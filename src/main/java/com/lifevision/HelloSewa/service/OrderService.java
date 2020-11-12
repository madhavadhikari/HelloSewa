package com.lifevision.HelloSewa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lifevision.HelloSewa.exception.NotFoundException;
import com.lifevision.HelloSewa.model.Order;
import com.lifevision.HelloSewa.model.OrderItem;
import com.lifevision.HelloSewa.model.Product;
import com.lifevision.HelloSewa.model.UserCart;
import com.lifevision.HelloSewa.repository.OrderItemRepository;
import com.lifevision.HelloSewa.repository.OrderRepository;
import com.lifevision.HelloSewa.repository.ProductRepository;
import com.lifevision.HelloSewa.repository.UserCartRepository;
import com.lifevision.HelloSewa.request.MultipleItemsRequest;
import com.lifevision.HelloSewa.request.ProductOrderItemInfo;
import com.lifevision.HelloSewa.response.ListingMyOrderResponse;
import com.lifevision.HelloSewa.response.OrderItemResponse;
import com.lifevision.HelloSewa.response.OrderProductsResponse;
import com.lifevision.HelloSewa.utils.CartItemStatus;
import com.lifevision.HelloSewa.utils.OrderItemStatus;
import com.lifevision.HelloSewa.utils.OrderStatus;
import com.lifevision.HelloSewa.utils.PaymentMethod;

@Service
public class OrderService {
	private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	UserCartRepository userCartRepository;
	
	private Float amount = 0f;
	
	/**
	 * orders a single product and returns its order_id.
	 * @param loginId
	 * @param token
	 * @param itemInfo
	 * @param shippingId
	 * @param billingId 
	 * @return Long
	 */
	public Long singleOrder(Long loginId, String token, ProductOrderItemInfo itemInfo, Long shippingId, Long billingId) {
		commonService.isValidToken(loginId, token);
		Order order = new Order();
		order.setCreatedDate(new Date());
		order.setOrderBy(loginId);
		order.setShippingAddressId(shippingId);
		order.setStatus(OrderStatus.INPROGRESS);
		order.setPaymentMethod(itemInfo.getPaymentMethod());
		order.setBillingAddressId(billingId);
		order.setVoucher(itemInfo.getVoucher());
		order.setDeliveryOption(itemInfo.getDeliveryOption());
		order.setDeliveryCost(0f);
		orderRepository.save(order);

		LOG.info("order id: "+order.getId());
		float total = setOrderItem(order, itemInfo);
		order.setAmount(total);
		
		orderRepository.save(order);
		return order.getId();				
	}
	/**
	 * orders multiple products and return the order_id.
	 
	 */
	public Long multipleProductOrder(Long loginId, String token, MultipleItemsRequest req, Long shippingId, Long billingId) {
		
		commonService.isValidToken(loginId, token);
		
		Order order = new Order();
		
		order.setCreatedDate(new Date());
		order.setOrderBy(loginId);
		order.setBillingAddressId(billingId);
		order.setPaymentMethod(req.getPaymentMethod());
		order.setVoucher(req.getVoucher());
		order.setShippingAddressId(shippingId);
		order.setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY);
		order.setDeliveryCost(0f);
		order.setDeliveryOption(req.getDeliveryOption());
		order.setStatus(OrderStatus.INPROGRESS);
		orderRepository.save(order);
		
		//saves the items of the order and returns each item total prices to save total amount in Order.
		List<Float> totalPrices = new ArrayList<>();
		req.getCartItemIds().forEach(eachId ->{
			float total = setOrderItemForMultiple(order, eachId);
			totalPrices.add(total);
		});
		//calculates the TotalAmount of each order item's total price in the Order
		totalPrices.forEach(price ->{
			amount +=price;
		});
		order.setAmount(amount);
		orderRepository.save(order);
		
		//for changing the status of user cart items as REMOVED.
		req.getCartItemIds().forEach(eachId ->{
			
			UserCart userCart = userCartRepository.getOne(eachId);
			
			userCart.setModifiedDate(new Date());
			userCart.setStatus(CartItemStatus.REMOVED);
			userCartRepository.save(userCart);
		});
		
		return order.getId();				
	}
	
	/**
	 * for ordering from user cart multiple items at a time.
	 * @param order
	 * @param item
	 * @return
	 */
	private float setOrderItemForMultiple(Order order, Long eachId) {
		
		UserCart userCart = userCartRepository.getOne(eachId);
		
		Product product = productRepository.getOne(userCart.getProduct().getId());
		
		OrderItem orderItem = new OrderItem(); 
		orderItem.setCreatedDate(new Date());
		orderItem.setOrder(order);
		orderItem.setQuantity(userCart.getQuantity());
		orderItem.setProduct(product);
		orderItem.setStatus(OrderItemStatus.ACTIVE);
		
		float total = userCart.getQuantity()*product.getSellingPrice();
		orderItem.setTotalPrice(total);
		
		orderItemRepository.save(orderItem);
	
		return total;
	}

	/**
	 * saves item of an order and return its total_price(quantity*price).
	 * @param order
	 * @param item
	 * @return float
	 */
	@Transactional
	public float setOrderItem(Order order, ProductOrderItemInfo item) {
		Product product = productRepository.getOne(item.getProductId());
		
		OrderItem orderItem = new OrderItem();
		orderItem.setCreatedDate(new Date());
		orderItem.setOrder(order);
		orderItem.setProduct(product);
		orderItem.setQuantity(item.getQuantity());
		orderItem.setStatus(OrderItemStatus.ACTIVE);
		
		float total = item.getQuantity()*product.getSellingPrice();
		orderItem.setTotalPrice(total);
		
		orderItemRepository.save(orderItem);
		
		return total;
	}
	
	/**
	 * Returns the list of all orders registered for given loginId expect the order removed.
	 * @param loginId
	 * @param token
	 * @return List<ListingMyOrderResponse>
	 */
	public List<ListingMyOrderResponse> listMyOrder(Long loginId, String token) {
		
		commonService.isValidToken(loginId, token);
		
		List<ListingMyOrderResponse> responseList = new ArrayList<>();
		List<Order> orderList = orderRepository.findByStatusNotAndOrderBy(OrderStatus.REMOVED, loginId);
		
		orderList.forEach(order ->{
			ListingMyOrderResponse listingMyOrderResponse = new ListingMyOrderResponse();
			
			listingMyOrderResponse.setOrderId(order.getId());
			listingMyOrderResponse.setId(order.getId());
			listingMyOrderResponse.setOrderedDate(order.getCreatedDate());
			listingMyOrderResponse.setOrderTotal(order.getAmount());
			listingMyOrderResponse.setTotalProducts(order.getItems().size());
			listingMyOrderResponse.setOrderStatus(order.getStatus());
			
			responseList.add(listingMyOrderResponse);
		});
		
		return responseList;
	}
	
	/**
	 * Returns the list of all items in an order(for given loginId) expect the items that are removed from that order.
	 * @param orderId
	 * @param loginId
	 * @param token
	 * @return List<OrderItemsResponse>
	 */
	public OrderItemResponse listMyOrderItems(Long orderId, Long loginId, String token) {
		
		commonService.isValidToken(loginId, token);
		
		Order order = orderRepository.getOne(orderId);
			
		OrderItemResponse finalResponse = new OrderItemResponse();
		
		finalResponse.setOrderId(order.getId());
		finalResponse.setOrderedDate(order.getCreatedDate());
		finalResponse.setOrderTotal(order.getAmount());
		finalResponse.setOrderStatus(order.getStatus());
		finalResponse.setPaymentMethod(order.getPaymentMethod());
		finalResponse.setDeliveryOption(order.getDeliveryOption());
		finalResponse.setBillinngAddressId(order.getBillingAddressId());
		finalResponse.setShippingAddressId(order.getShippingAddressId());
		
		List<OrderItem> products = orderItemRepository.findByOrderAndStatus(order, OrderItemStatus.ACTIVE);
		
		List<OrderProductsResponse> allResponses = new ArrayList<>();
		
		products.forEach(p -> {
			
			OrderProductsResponse res = new OrderProductsResponse();
			
			//setting response
			res.setProductId(p.getProduct().getId());
			res.setProductName(p.getProduct().getName());
			res.setProductBrand(p.getProduct().getBrandId());
			res.setOrderItemId(p.getId());
			res.setProductPrice(p.getProduct().getSellingPrice());
			res.setQuantity(p.getQuantity());
			res.setThumbnail(p.getProduct().getThumbnail());
			res.setTotalPrice(p.getTotalPrice());
			allResponses.add(res);
			
			finalResponse.setOrderProductsResponse(allResponses);
		});
		return finalResponse;
	
	}
	
	/**
	 * Change the Status of Order as COMPLETED.
	 * @param orderId
	 * @param loginId
	 * @param token
	 */
	public void completeOrder(Long orderId, Long loginId, String token) {

		commonService.isValidToken(loginId, token);
		Order order = orderRepository.getOne(orderId);
		if(order == null)
			throw new NotFoundException("Order not found.");
		
		order.setStatus(OrderStatus.COMPLETED);
		orderRepository.save(order);
	}

	/**
	 * Change the Status of Order as CANCELED.
	 * @param orderId
	 * @param loginId
	 * @param token
	 */
	public void cancelOrder(Long orderId, Long loginId, String token) {
		
		commonService.isValidToken(loginId, token);
		Order order = orderRepository.getOne(orderId);
		if(order == null)
			throw new NotFoundException("Order not found.");
		
		order.setStatus(OrderStatus.CANCELED);
		orderRepository.save(order);		
	}

	/**
	 * Change the Status of Order as REMOVED.
	 * @param orderId
	 * @param loginId
	 * @param token
	 */
	public void removeOrder(Long orderId, Long loginId, String token) {
		
		commonService.isValidToken(loginId, token);
		Order order = orderRepository.getOne(orderId);
		if(order == null)
			throw new NotFoundException("Order not found.");
		
		order.setStatus(OrderStatus.REMOVED);
		orderRepository.save(order);
	}

	/**
	 * Method for cancel items of particular order.
	 * @param orderId
	 * @param loginId
	 * @param orderItemId
	 * @param token
	 */
	public void cancelOrderItems(Long loginId, String token, Long orderId, Long orderItemId) {
		 
		commonService.isValidToken(loginId, token);
		
		OrderItem orderItem = orderItemRepository.getOne(orderItemId);
		if(orderItem == null)
			throw new NotFoundException("Order Item not found.");
		
		orderItem.setStatus(OrderItemStatus.REMOVED);
		orderItemRepository.save(orderItem);	
		
		Order order = orderRepository.getOne(orderId);
		float previousamount = order.getAmount();
		float dd = orderItem.getTotalPrice();
		order.setAmount(previousamount - dd);
		orderRepository.save(order);

	}	
}
