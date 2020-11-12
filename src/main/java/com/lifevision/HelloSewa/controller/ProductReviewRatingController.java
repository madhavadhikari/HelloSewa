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

import com.lifevision.HelloSewa.request.ProductReviewRatingCreationRequest;
import com.lifevision.HelloSewa.response.ProductReviewRatingRes;
import com.lifevision.HelloSewa.response.ProductReviewRatingResponse;
import com.lifevision.HelloSewa.service.ProductReviewRatingService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api/v1/productreviewrating")
public class ProductReviewRatingController {

	@Autowired
	ProductReviewRatingService productReviewRatingService;
	
	
	@PostMapping("/add")
	@ApiOperation(
			value = "Api for giving review and rating on product.",
			notes = "This api is for posting review and rating on product.")
	public ResponseEntity<Object> addProductReviewRating(@RequestHeader Long loginId, @RequestHeader String token,
			@RequestHeader Long productId, @RequestBody ProductReviewRatingCreationRequest request){
		productReviewRatingService.addProductReviewRating(loginId, token, productId, request);
		return new ResponseEntity<Object>("Review and rating saved.", HttpStatus.OK);	
	}
	
	@PutMapping("/remove")
	@ApiOperation(
			value = "Api for removing review and rating on product.",
			notes = "This api is for removing review and rating on product.")
	public ResponseEntity<Object> removeProductReviewRating(@RequestHeader Long loginId, @RequestHeader String token,
			@RequestHeader Long productReviewRatingId) {
		productReviewRatingService.removeProductReviewRating(loginId, token, productReviewRatingId);
		return new ResponseEntity<Object>("Review and rating removed.", HttpStatus.OK);	
	}

	@GetMapping("/get/all")
	@ApiOperation(
			value = "Api for getting all review and rating of particular users.",
			notes = "This api is for getting product rating and review of particular user.",
			response = ProductReviewRatingResponse.class,
			responseContainer = "List")
	public ResponseEntity<Object> getAllProductReviewRating(@RequestHeader Long loginId, @RequestHeader String token) {
		List<ProductReviewRatingResponse> response= productReviewRatingService.getAllProductReviewRating(loginId, token);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@GetMapping("/get/{productId}")
	@ApiOperation(
			value = "Api for getting all review and rating of particular product.",
			notes = "This api for getting product rating and review for particular product.",
			response = ProductReviewRatingResponse.class)
	public ResponseEntity<Object> getReviewRatingForProduct(@PathVariable Long productId) {
		ProductReviewRatingRes response= productReviewRatingService.getReviewRatingForProduct(productId);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}
