package com.lifevision.HelloSewa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifevision.HelloSewa.exception.NotFoundException;
import com.lifevision.HelloSewa.model.Product;
import com.lifevision.HelloSewa.model.UserCart;
import com.lifevision.HelloSewa.model.Wishlist;
import com.lifevision.HelloSewa.repository.LoginRepository;
import com.lifevision.HelloSewa.repository.ProductRepository;
import com.lifevision.HelloSewa.repository.UserCartRepository;
import com.lifevision.HelloSewa.repository.WishlistRepository;
import com.lifevision.HelloSewa.response.UserCartResponse;
import com.lifevision.HelloSewa.utils.CartItemStatus;
import com.lifevision.HelloSewa.utils.WishlistItemStatus;

@Service
public class UserCartService {
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	UserCartRepository userCartRepository;
	
	@Autowired
	LoginRepository loginRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	WishlistRepository wishlistRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserCartService.class);
	
	/**
	 * add product to user's cart
	 * @param loginId, productId
	 * @param token
	 * @return integer
	 */
	public int addUserCart(Long loginId, String token, Long productId) {

		commonService.isValidToken(loginId, token);
		
		UserCart checkUserCart = userCartRepository.findByLoginIdAndProductIdAndStatus(loginId, productId, CartItemStatus.ACTIVE);
		if(checkUserCart != null) {
			
			checkUserCart.setModifiedDate(new Date());
			checkUserCart.setQuantity(checkUserCart.getQuantity() + 1);
			userCartRepository.save(checkUserCart);
		}
		
		else{
			UserCart userCart = new UserCart();
			Product product = productRepository.getOne(productId);
			userCart.setCreatedDate(new Date());
			userCart.setLoginId(loginId);
			userCart.setProduct(product);
			userCart.setQuantity(1);
			userCart.setStatus(CartItemStatus.ACTIVE);
			userCartRepository.save(userCart);
		}
			List<UserCart> userCarts = userCartRepository.findByLoginIdAndStatusNot(loginId, CartItemStatus.REMOVED);
			return userCarts.size();	
	}


	/**
	 * get all user's cart items 
	 * @param loginId
	 * @param token
	 * @return List<UserCartResponse>
	 */
	public List<UserCartResponse> getUserCarts(Long loginId, String token) {

		commonService.isValidToken(loginId, token);
		
		List<UserCart> userCarts = userCartRepository.findByLoginIdAndStatusNot(loginId, CartItemStatus.REMOVED);
		LOG.info("Number of carts: "+userCarts.size());
		
		List<UserCartResponse> allCarts = new ArrayList<>();
		
		userCarts.forEach(u -> {
			
			UserCartResponse res = new UserCartResponse();
			
			//checking product in wish list table. 
			Wishlist wishlist = wishlistRepository.findByLoginIdAndProductIdAndStatus(loginId, u.getProduct().getId(), WishlistItemStatus.ACTIVE);
			if(wishlist != null) {
				res.setWishlistStatus(WishlistItemStatus.ACTIVE);
			}else 
			res.setWishlistStatus(WishlistItemStatus.REMOVED);
			
			//setting responses
			res.setId(u.getId());
			res.setCreatedDate(u.getCreatedDate());
			res.setId(u.getId());
			res.setQuantity(u.getQuantity());
			res.setName(u.getProduct().getName());
			res.setThumbnail(u.getProduct().getThumbnail());
			res.setStock(u.getProduct().getStock());
			res.setSellingPrice(u.getProduct().getSellingPrice());
			res.setPrice(u.getProduct().getPrice());
			res.setProductId(u.getProduct().getId());
			res.setStatus(u.getStatus());
			res.setDiscount(u.getProduct().getDiscount());
			res.setLoginId(u.getLoginId());
			
			allCarts.add(res);
		});
		return allCarts;
	}

	/**
	 * remove individual item from user cart.
	 * @param loginId, userCartId
	 * @param token
	 */
	public void deleteUserCart(Long loginId, String token, Long userCartId) {
		
		commonService.isValidToken(loginId, token);
		
		UserCart userCart = userCartRepository.getOne(userCartId);
	
		userCart.setModifiedDate(new Date());
		userCart.setStatus(CartItemStatus.REMOVED);
		userCartRepository.save(userCart);
	}
	
	/**
	 * remove all items from user cart for any user.
	 * @param loginId
	 * @param token
	 */
	public void deleteAllUserCarts(Long loginId, String token) {
		
		commonService.isValidToken(loginId, token);
		
		List<UserCart> userCarts = userCartRepository.findByLoginIdAndStatusNot(loginId, CartItemStatus.REMOVED);
		
		for (UserCart u : userCarts) {
			
			u.setModifiedDate(new Date());
			u.setStatus(CartItemStatus.REMOVED);
			userCartRepository.save(u);
		}	
	}
	
	/**
	 * update the product quantity in the cart list.
	 * @param loginId
	 * @param token
	 * @param userCartId
	 * @param quantity
	 */
	public void updateProductQuantity(Long loginId, String token, Long userCartId, int quantity) {
		
		commonService.isValidToken(loginId, token);
		
		UserCart userCart = userCartRepository.findByIdAndStatus(userCartId, CartItemStatus.ACTIVE);
		if(userCart == null)
			throw new NotFoundException("Cart ID invalid");
		userCart.setQuantity(quantity);
		userCart.setModifiedDate(new Date());
		
		userCartRepository.save(userCart);
	}

	/**
	 * counts the items in user's cart.
	 * @param loginId
	 * @param token
	 * @return size
	 */
	public int countUserCart(Long loginId, String token) {
		
		commonService.isValidToken(loginId, token);
		
		List<UserCart> userCarts = userCartRepository.findByLoginIdAndStatusNot(loginId, CartItemStatus.REMOVED);
		return userCarts.size();
	}
}
