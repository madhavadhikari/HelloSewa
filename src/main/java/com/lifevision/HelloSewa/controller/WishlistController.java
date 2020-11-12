package com.lifevision.HelloSewa.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lifevision.HelloSewa.response.WishlistResponse;
import com.lifevision.HelloSewa.service.CommonService;
import com.lifevision.HelloSewa.service.WishlistService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("api/v1/wishlist")
public class WishlistController {
	
private static final Logger LOG = LoggerFactory.getLogger(WishlistController.class);
	
	@Autowired
	WishlistService wishlistService;
	
	@Autowired
	CommonService commonService;
	
	@PostMapping("/add")
	@ApiOperation(
			value = "API to adding product on wishlist.",
			notes = "This API is used for adding product on wishlist.")
	public ResponseEntity<Object> addWishlist(@RequestHeader Long loginId, 
			@RequestHeader String token, @RequestHeader Long productId){
		wishlistService.addWishlist(loginId, token, productId);
		return new ResponseEntity<Object>("Item sucessfully updated to wishlist.", HttpStatus.CREATED);	
	}
	
	@GetMapping("/get")
	@ApiOperation(
			value = "API to getting all products of user's wishlist.",
			notes = "This API is used for getting all products of user's wishlist.",
			response = WishlistResponse.class,
			responseContainer = "List")
	public ResponseEntity<Object> getUserCarts(@RequestHeader Long loginId, @RequestHeader String token){
		List<WishlistResponse> wishlists = wishlistService.getWishlist(loginId, token);
		LOG.info("Number of items in wish list: "+wishlists.size());
		return new ResponseEntity<Object>(wishlists, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	@ApiOperation(
			value = "API for removing individual product from wishlist.",
			notes = "This Api is for removing individual item for wish list using productId.")
	public ResponseEntity<Object> deleteWishlist(@RequestHeader Long loginId,
			@RequestHeader String token, @RequestHeader Long productId){
		wishlistService.deleteWishlist(loginId, token, productId);
		return new ResponseEntity<Object>("item removed sucessfully from wish list.", HttpStatus.OK);
	}

	@DeleteMapping("/deleteAllWishlists")
	@ApiOperation(
			value = "API for clear wishlist.",
			notes = "This Api is for clear wishlist.")
	public ResponseEntity<Object> deleteAllWishlists(@RequestHeader Long loginId, @RequestHeader String token){
		wishlistService.deleteAllWishlists(loginId, token);
		return new ResponseEntity<Object>("Wishlist has been clerared sucessfully", HttpStatus.OK);
	}
	
	@GetMapping("/check-item")
	@ApiOperation(
			value = "API for chack item on wishlist.",
			notes = "This Api is for check product in wishlist.")
	public ResponseEntity<Object> checkItemInWishlist(@RequestHeader Long loginId, 
			@RequestHeader String token, @RequestHeader Long productId){
		String status = wishlistService.checkItemInWishlist(loginId, productId, token);
		return new ResponseEntity<Object>(status, HttpStatus.OK);
	}
}
