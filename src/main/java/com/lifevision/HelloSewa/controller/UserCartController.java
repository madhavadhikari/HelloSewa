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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lifevision.HelloSewa.response.UserCartResponse;
import com.lifevision.HelloSewa.service.UserCartService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("api/v1/usercart")
public class UserCartController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserCartController.class);
	
	@Autowired
	UserCartService userCartService;
	
	@PostMapping("/add")
	@ApiOperation(
			value = "API for adding user cart.",
			notes = "This api for posting products on usercart.")
	public ResponseEntity<Object> addUserCart(@RequestHeader Long loginId, @RequestHeader String token, 
			@RequestHeader Long productId){
		int count = userCartService.addUserCart(loginId, token, productId);
		return new ResponseEntity<Object>("Product already in user cart. Only number of items increased by 1.", HttpStatus.CREATED);	
	}
	
	@GetMapping("/get")
	@ApiOperation(
			value = "API for getting user carts.", 
			notes = "This api is for getting user cart items for particular user.",
			response = UserCartResponse.class,
			responseContainer = "List")
	public ResponseEntity<Object> getUserCarts(@RequestHeader Long loginId, @RequestHeader String token){
		List<UserCartResponse> userCarts = userCartService.getUserCarts(loginId, token);
		LOG.info("Number of items in cart: "+userCarts.size());
		return new ResponseEntity<Object>(userCarts, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	@ApiOperation(
			value = "API for removing single product on user cart.", 
			notes = "This api is for removing particular item from user cart.")
	public ResponseEntity<Object> deleteUserCart(@RequestHeader Long loginId, @RequestHeader String token, 
			@RequestHeader Long userCartId){
		userCartService.deleteUserCart(loginId, token, userCartId);
		return new ResponseEntity<Object>("item removed sucessfully", HttpStatus.OK);
	}

	@DeleteMapping("/deleteAllUserCarts")
	@ApiOperation(
			value = "API for clear user cart.", 
			notes = "This api is for clear the user cart.")
	public ResponseEntity<Object> deleteAllUserCarts(@RequestHeader Long loginId, @RequestHeader String token){
		userCartService.deleteAllUserCarts(loginId, token);
		return new ResponseEntity<Object>("Cart has been clerared sucessfully", HttpStatus.OK);
	}
		
	@PutMapping("/update-quantity/{userCartId}")
	@ApiOperation(
			value = "API to update the quantity of product of user's cart",
			notes = "This api is for updating quantity of products in cart.")
	public ResponseEntity<Object> updateProductQuantity(@RequestHeader Long loginId, @RequestHeader String token,
			@PathVariable Long userCartId, @RequestParam int newQuantity){
		userCartService.updateProductQuantity(loginId, token, userCartId, newQuantity);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@GetMapping("/count")
	@ApiOperation(
			value = "API to count the number of items of user's cart.",
			notes = "This is the api for counting the total number products in user cart.")
	public ResponseEntity<Object> countUserCart(@RequestHeader Long loginId, @RequestHeader String token){
		int count = userCartService.countUserCart(loginId, token);
		return new ResponseEntity<Object>(count, HttpStatus.OK);
	}	
}
