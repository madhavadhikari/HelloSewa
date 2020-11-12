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
import com.lifevision.HelloSewa.model.Wishlist;
import com.lifevision.HelloSewa.repository.LoginRepository;
import com.lifevision.HelloSewa.repository.ProductRepository;
import com.lifevision.HelloSewa.repository.UserCartRepository;
import com.lifevision.HelloSewa.repository.WishlistRepository;
import com.lifevision.HelloSewa.response.WishlistResponse;
import com.lifevision.HelloSewa.utils.WishlistItemStatus;

@Service
public class WishlistService {
	
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
	
	private static final Logger LOG = LoggerFactory.getLogger(WishlistService.class);

	/**
	 * add product to user's wish list.
	 * @param loginId, productId
	 * @param token
	 */
	public void addWishlist(Long loginId, String token, Long productId) {
		
		commonService.isValidToken(loginId, token);
		
		Wishlist checkWishlist = wishlistRepository.findByLoginIdAndProductIdAndStatusNot(loginId, productId, WishlistItemStatus.REMOVED);
		if(checkWishlist == null) {
			
			Product product = productRepository.getOne(productId);
			Wishlist wishlist = new Wishlist();
			wishlist.setCreatedDate(new Date());
			wishlist.setLoginId(loginId);
			wishlist.setProduct(product);
			wishlist.setStatus(WishlistItemStatus.ACTIVE);
			wishlistRepository.save(wishlist);	
		}else 
			throw new NotFoundException("Product already added to wishlist.");
	}
	
	/**
	 * get all user's wish list items.
	 * @param loginId
	 * @param token
	 * @return List<WishlistResponse>
	 */
	public List<WishlistResponse> getWishlist(Long loginId, String token) {

		commonService.isValidToken(loginId, token);
		
		List<Wishlist> wishlists = wishlistRepository.findByLoginIdAndStatusNot(loginId, WishlistItemStatus.REMOVED);
		LOG.info("Number of items in wishlist: "+wishlists.size());
		
		List<WishlistResponse> allWishlists = new ArrayList<>();
		
		wishlists.forEach(w -> {
			
			WishlistResponse res = new WishlistResponse();
	
			//setting responses
			res.setId(w.getId());
			res.setStatus(w.getStatus());
			res.setCreatedDate(w.getCreatedDate());
			res.setName(w.getProduct().getName());
			res.setThumbnail(w.getProduct().getThumbnail());
			res.setSellingPrice(w.getProduct().getSellingPrice());
			res.setPrice(w.getProduct().getPrice());
			res.setProductId(w.getProduct().getId());
			res.setLoginId(w.getLoginId());
			res.setDiscount(w.getProduct().getDiscount());
			
			allWishlists.add(res);
		});
		
		return allWishlists;
	}

	/**
	 * remove individual item from user's wish list.
	 * @param loginId, productId
	 * @param token
	 */
	public void deleteWishlist(Long loginId, String token, Long productId) {

		commonService.isValidToken(loginId, token);
		
		Wishlist wishlist = wishlistRepository.findByLoginIdAndProductIdAndStatusNot(loginId, productId, WishlistItemStatus.REMOVED);
		if(wishlist == null)
			throw new NotFoundException("Product not found to remove.");
		
		else
		wishlist.setModifiedDate(new Date());
		wishlist.setStatus(WishlistItemStatus.REMOVED);
		wishlistRepository.save(wishlist);
	}

	/**
	 * remove all items from user's wish list for any user.
	 * @param loginId
	 * @param token
	 */
	public void deleteAllWishlists(Long loginId, String token) {

		commonService.isValidToken(loginId, token);
		
		List<Wishlist> wishlits = wishlistRepository.findByLoginIdAndStatusNot(loginId, WishlistItemStatus.REMOVED);
		for (Wishlist w : wishlits) {
			
			w.setModifiedDate(new Date());
			w.setStatus(WishlistItemStatus.REMOVED);
			wishlistRepository.save(w);
		}	
	}

	/**
	 * Checks product in wish list.
	 * @param loginId
	 * @param ProductId
	 * @param token
	 * @return String
	 */
	public String checkItemInWishlist(Long loginId, Long productId, String token) {
		
		Wishlist wishlist = wishlistRepository.findByLoginIdAndProductIdAndStatusNot(loginId, productId, WishlistItemStatus.REMOVED);
		if(wishlist != null) {
			return WishlistItemStatus.ACTIVE.name();
		}
		else {
			return WishlistItemStatus.REMOVED.name();
		}
     }
}
