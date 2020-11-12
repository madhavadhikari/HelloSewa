package com.lifevision.HelloSewa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lifevision.HelloSewa.request.ProductCreationRequest;
import com.lifevision.HelloSewa.request.ProductEditRequest;
import com.lifevision.HelloSewa.response.ProductResponse;
import com.lifevision.HelloSewa.service.ProductService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("api/v1/product")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/add")
	@ApiOperation(
			value = "Api to adding product.",
			notes = "This api is for posting product.")
	public ResponseEntity<Object> createProduct(@RequestHeader Long loginId, @RequestHeader String token, 
			@RequestBody ProductCreationRequest productCreationRequest){
		Long id = productService.addProduct(loginId, token, productCreationRequest);
 		return new ResponseEntity<Object>("Product added sucessfully with id "+id, HttpStatus.OK);
	}
	
	@GetMapping("/get/{productId}")
	@ApiOperation(
			value = "Get a particular Product API.", 
			notes = "Various informations will be provided when product is successfully retrieved for given ID.", 
			response = ProductResponse.class)
	public ResponseEntity<Object> getProduct(@PathVariable Long productId){
		ProductResponse productResponse = productService.getProduct(productId);
 		return new ResponseEntity<Object>(productResponse, HttpStatus.OK);
	}
	
	@GetMapping("/getAllProducts")
	@ApiOperation(
				value ="get all product API.",
				notes = "API for returning all the products from databases.",
				response = ProductResponse.class)
	public ResponseEntity<Object> getAllProducts(){
		List<ProductResponse> productResponse = productService.getAllProducts();
 		return new ResponseEntity<Object>(productResponse, HttpStatus.OK);
	}
	
	@PutMapping("/edit")
	@ApiOperation(
			value = "edit product API",
			notes = "API for editing the contents of particular product.")
	public ResponseEntity<Object> editProduct(@RequestHeader Long loginId, @RequestHeader String token, 
			@RequestBody ProductEditRequest productEditRequest){
		productService.editProduct(loginId, token, productEditRequest);
		return new ResponseEntity<Object>("Edit sucessfully. ", HttpStatus.OK);
	}
	
	@GetMapping("/get/byBrand/{brandId}")
	@ApiOperation(
			value = "get product API according to brandId.", 
			notes = "This api is for getting specific items according to brand.",
			response = ProductResponse.class,
			responseContainer = "List")
	public ResponseEntity<Object> getProductsByBrand(@PathVariable int brandId){
		List<ProductResponse> productResponse = productService.getProductsByBrand(brandId);
 		return new ResponseEntity<Object>(productResponse, HttpStatus.OK);
	}
	
	@GetMapping("/get/byCategory/{categoryId}")
	@ApiOperation(
			value = "get product API according to categoryId.", 
			notes = "This api is for getting specific items according to category.",
			response = ProductResponse.class,
			responseContainer = "List")
	public ResponseEntity<Object> getProductsByCategory(@PathVariable int categoryId){
		List<ProductResponse> productResponse = productService.getProductsByCategory(categoryId);
 		return new ResponseEntity<Object>(productResponse, HttpStatus.OK);
	}
}
