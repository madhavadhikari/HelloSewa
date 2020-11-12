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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lifevision.HelloSewa.request.ShippingAddressEditRequest;
import com.lifevision.HelloSewa.request.ShippingAddressRequest;
import com.lifevision.HelloSewa.response.ShippingAddressResponse;
import com.lifevision.HelloSewa.service.ShippingAddressService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("api/v1/shippingaddress")
public class ShippingAddressController {
	
	@Autowired
	ShippingAddressService shippingAddressService;

	private static final Logger LOG = LoggerFactory.getLogger(ShippingAddressController.class);
	
	@PostMapping("/add")
	@ApiOperation(
			value = "Api to posting Shipping Address.",
			notes = "This api is for adding shipping address.")
	public ResponseEntity<Object> addShippingAddress(@RequestHeader Long loginId, @RequestHeader String token, 
			@RequestBody ShippingAddressRequest request){
		Long id = shippingAddressService.addShippingAddress(loginId, token, request);
		return new ResponseEntity<Object>("Shipping Address added successfully with id "+id, HttpStatus.CREATED);	
	}
	
	@GetMapping("/get")
	@ApiOperation(
			value = "Response of Shipping Address",
			notes = "This is for getting Shipping address for particular user.",
			response = ShippingAddressResponse.class, 
			responseContainer = "List")
	public ResponseEntity<Object> getShippingAddresses(@RequestHeader Long loginId, @RequestHeader String token){
		List<ShippingAddressResponse> shippingAddresses = shippingAddressService.getShippingAddresses(loginId, token);
		LOG.info("Number of addresses : "+shippingAddresses.size());
		return new ResponseEntity<Object>(shippingAddresses, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	@ApiOperation(
			value = "API for removing shipping address.",
			notes = "This api for removing shipping address.")
	public ResponseEntity<Object> deleteShippingAddress(@RequestHeader Long loginId, @RequestHeader String token, 
			@RequestHeader Long shippingAddressId){
		shippingAddressService.deleteShippingAddress(loginId, token, shippingAddressId);
		return new ResponseEntity<Object>("Shipping address removed sucessfully.", HttpStatus.OK);
	}
	
	@PutMapping("/edit")
	@ApiOperation(
			value = "API for editing shipping address.",
			notes = "This api for editing shipping address.")
	public ResponseEntity<Object> editShippingAddress(@RequestHeader Long loginId, @RequestHeader String token, 
			@RequestBody ShippingAddressEditRequest request){
		shippingAddressService.editShippingAddress(loginId, token, request);
		return new ResponseEntity<Object>("Address Edited Sucessfully.",  HttpStatus.OK);
	}
	
	@GetMapping("/get/{shippingAddressId}")
	@ApiOperation(
			value = "API for removing shipping address.",
			notes = "This api for removing shipping address.",
			response = ShippingAddressResponse.class)
	public ResponseEntity<Object> getShippingAddress(@RequestHeader Long loginId, 
			@RequestHeader String token, @PathVariable Long shippingAddressId ){
		ShippingAddressResponse shippingAddress = shippingAddressService.getShippingAddress(loginId, token, shippingAddressId);
		return new ResponseEntity<Object>(shippingAddress, HttpStatus.OK);
   }
	
	@GetMapping("/get/defaultShippingAddress")
	@ApiOperation(
			value = "API for removing shipping address.",
			notes = "This api for removing shipping address.",
			response = ShippingAddressResponse.class)
	public ResponseEntity<Object> getDefaultShippingAddress(@RequestHeader Long loginId, @RequestHeader String token){
		ShippingAddressResponse shippingAddress = shippingAddressService.getDefaultShippingAddress(loginId, token);
		return new ResponseEntity<Object>(shippingAddress, HttpStatus.OK);
   }
}

